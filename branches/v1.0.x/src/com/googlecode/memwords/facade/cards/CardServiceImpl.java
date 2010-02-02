package com.googlecode.memwords.facade.cards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletResponse;

import org.xml.sax.InputSource;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.ResponseTooLargeException;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.Card;
import com.googlecode.memwords.domain.CardBasicInformation;
import com.googlecode.memwords.domain.CardBasicInformationComparator;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.util.CryptoEngine;

/**
 * Implementation of {@link CardService}
 * @author JB
 */
@Singleton
public class CardServiceImpl implements CardService {

    private EntityManager em;
    private CryptoEngine cryptoEngine;
    private URLFetchService urlFetchService;
    private FavIconFinder favIconFinder;

    @Inject
    public CardServiceImpl(EntityManager em,
                           CryptoEngine cryptoEngine,
                           URLFetchService urlFetchService,
                           FavIconFinder favIconFinder) {
        this.em = em;
        this.cryptoEngine = cryptoEngine;
        this.urlFetchService = urlFetchService;
        this.favIconFinder = favIconFinder;
    }

    @Override
    public List<CardBasicInformation> getCards(String userId, SecretKey encryptionKey) {
        List<Card> cards = (em.find(Account.class, userId)).getCards();
        List<CardBasicInformation> result = new ArrayList<CardBasicInformation>(cards.size());
        for (Card card : cards) {
            result.add(new CardBasicInformation(card.getId(),
                                                cryptoEngine.decryptString(card.getName(),
                                                                           encryptionKey,
                                                                           card.getInitializationVector()),
                                                cryptoEngine.decryptString(card.getIconUrl(),
                                                                           encryptionKey,
                                                                           card.getInitializationVector())));
        }
        Collections.sort(result, CardBasicInformationComparator.INSTANCE);
        return result;
    }

    @Override
    public boolean cardExists(String userId,
                              String name,
                              String cardId,
                              SecretKey encryptionKey) {
        List<Card> cards = (em.find(Account.class, userId)).getCards();
        for (Card card : cards) {
            if (!card.getId().equals(cardId)) {
                String cardName =
                    cryptoEngine.decryptString(card.getName(),
                                               encryptionKey,
                                               card.getInitializationVector());
                if (cardName.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Card createCard(String userId, CardDetails cardDetails, SecretKey encryptionKey) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Card card = new Card();
            card.setInitializationVector(cryptoEngine.generateInitializationVector());
            updateCard(cardDetails, encryptionKey, card, true);
            Account account = em.find(Account.class, userId);
            account.addCard(card);
            em.persist(card);
            tx.commit();
            return card;
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    private void updateCard(CardDetails cardDetails,
                            SecretKey encryptionKey,
                            Card card,
                            boolean modifyPassword) {
        card.setName(cryptoEngine.encryptString(cardDetails.getName(),
                                                encryptionKey,
                                                card.getInitializationVector()));
        card.setLogin(cryptoEngine.encryptString(cardDetails.getLogin(),
                                                 encryptionKey,
                                                 card.getInitializationVector()));
        if (modifyPassword) {
            card.setPassword(cryptoEngine.encryptString(cardDetails.getPassword(),
                                                        encryptionKey,
                                                        card.getInitializationVector()));
        }
        card.setUrl(cryptoEngine.encryptString(cardDetails.getUrl(),
                                               encryptionKey,
                                               card.getInitializationVector()));
        card.setIconUrl(cryptoEngine.encryptString(cardDetails.getIconUrl(),
                                                   encryptionKey,
                                                   card.getInitializationVector()));
        card.setNote(cryptoEngine.encryptString(cardDetails.getNote(),
                                                encryptionKey,
                                                card.getInitializationVector()));
    }

    @Override
    public Card modifyCard(CardDetails cardDetails, SecretKey encryptionKey, boolean modifyPassword) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Card card = em.find(Card.class, cardDetails.getId());
            updateCard(cardDetails, encryptionKey, card, modifyPassword);
            tx.commit();
            return card;
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public CardDetails getCardDetails(String cardId, SecretKey encryptionKey) {
        Card card = em.find(Card.class, cardId);
        if (card == null) {
            return null;
        }
        return new CardDetails(
                card.getId(),
                cryptoEngine.decryptString(card.getName(),
                                           encryptionKey,
                                           card.getInitializationVector()),
                cryptoEngine.decryptString(card.getLogin(),
                                           encryptionKey,
                                           card.getInitializationVector()),
                cryptoEngine.decryptString(card.getPassword(),
                                           encryptionKey,
                                           card.getInitializationVector()),
                cryptoEngine.decryptString(card.getUrl(),
                                           encryptionKey,
                                           card.getInitializationVector()),
                cryptoEngine.decryptString(card.getIconUrl(),
                                           encryptionKey,
                                           card.getInitializationVector()),
                 cryptoEngine.decryptString(card.getNote(),
                                            encryptionKey,
                                            card.getInitializationVector()));

    }

    @Override
    public void deleteCard(String cardId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Card card = em.find(Card.class, cardId);
            em.remove(card);
            tx.commit();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public String findFavIconUrl(String urlAsString) throws FavIconException {
        if (!urlAsString.startsWith("http://") && !urlAsString.startsWith("https://")) {
            throw new FavIconException("The URL must start with http:// or https://");
        }

        try {
            URL url = new URL(urlAsString);
            try {
                HTTPResponse response = urlFetchService.fetch(url);
                if (response != null) {
                    URL finalUrl = url;
                    if (response.getFinalUrl() != null) {
                        finalUrl = response.getFinalUrl();
                    }
                    InputSource inputSource =
                        new InputSource(new ByteArrayInputStream(response.getContent()));
                    String iconUrl = favIconFinder.findFavIcon(inputSource, finalUrl);
                    if (iconUrl != null) {
                        return iconUrl;
                    }
                }
                try {
                    URI baseUri = url.toURI();
                    URI defaultFavIconUri = baseUri.resolve("/favicon.ico");
                    response = urlFetchService.fetch(defaultFavIconUri.toURL());
                    if (response == null || response.getResponseCode() != HttpServletResponse.SC_OK) {
                        return null;
                    }
                    else {
                        return defaultFavIconUri.toString();
                    }
                }
                catch (URISyntaxException e) {
                    throw new FavIconException(e);
                }
            }
            catch (IOException e) {
                throw new FavIconException(e);
            }
            catch (ResponseTooLargeException e) {
                throw new FavIconException(e);
            }
        }
        catch (MalformedURLException e) {
            throw new FavIconException(e);
        }
    }
}
