package com.googlecode.memwords.facade.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.Card;
import com.googlecode.memwords.domain.CardBasicInformation;
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
	
	@Inject
	public CardServiceImpl(EntityManager em, CryptoEngine cryptoEngine) {
		this.em = em;
		this.cryptoEngine = cryptoEngine;
	}
	
	@Override
	public List<CardBasicInformation> getCards(String userId, SecretKey encryptionKey) {
		List<Card> cards = ((Account) em.find(Account.class, userId)).getCards();
		List<CardBasicInformation> result = new ArrayList<CardBasicInformation>(cards.size());
		for (Card card : cards) {
			result.add(new CardBasicInformation(card.getId(), 
					                            cryptoEngine.decryptString(card.getName(), encryptionKey),
					                            cryptoEngine.decryptString(card.getIconUrl(), encryptionKey)));
		}
		Collections.sort(result);
		return result;
	}
	
	@Override
	public boolean cardExists(String userId, String name) {
		List<Card> cards = ((Account) em.find(Account.class, userId)).getCards();
		for (Card card : cards) {
			if (card.getName().equals(name)) {
				return true;
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
			card.setName(cryptoEngine.encryptString(cardDetails.getName(), encryptionKey));
			card.setLogin(cryptoEngine.encryptString(cardDetails.getLogin(), encryptionKey));
			card.setPassword(cryptoEngine.encryptString(cardDetails.getPassword(), encryptionKey));
			card.setUrl(cryptoEngine.encryptString(cardDetails.getUrl(), encryptionKey));
			card.setIconUrl(cryptoEngine.encryptString(cardDetails.getIconUrl(), encryptionKey));
			Account account = ((Account) em.find(Account.class, userId));
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
	
	@Override
	public CardDetails getCardDetails(String cardId, SecretKey encryptionKey) {
		Card card = (Card) em.find(Card.class, cardId);
		return new CardDetails(
				card.getId(), 
				cryptoEngine.decryptString(card.getName(), encryptionKey),
				cryptoEngine.decryptString(card.getLogin(), encryptionKey),
				cryptoEngine.decryptString(card.getPassword(), encryptionKey),
				cryptoEngine.decryptString(card.getUrl(), encryptionKey),
                cryptoEngine.decryptString(card.getIconUrl(), encryptionKey));
	}
	
	@Override
	public void deleteCard(String cardId) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Card card = (Card) em.find(Card.class, cardId);
			em.remove(card);
			tx.commit();
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
}
