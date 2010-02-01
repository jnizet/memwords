package com.googlecode.memwords.facade.cards;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.googlecode.memwords.domain.Card;
import com.googlecode.memwords.domain.CardBasicInformation;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.facade.account.AccountServiceImpl;
import com.googlecode.memwords.facade.util.CryptoEngine;
import com.googlecode.memwords.facade.util.CryptoEngineImpl;
import com.googlecode.memwords.test.util.GAETestCase;

public class CardServiceImplTest extends GAETestCase {

    private EntityManager em;
    private CryptoEngine cryptoEngine;
    private URLFetchService mockUrlFetchService;
    private FavIconFinder mockFavIconFinder = createMock(FavIconFinder.class);
    private CardServiceImpl impl;

    private String userId = "userId";
    private SecretKey encryptionKey;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        em = createEntityManager();
        cryptoEngine = new CryptoEngineImpl();
        mockUrlFetchService = createMock(URLFetchService.class);
        impl = new CardServiceImpl(em, cryptoEngine, mockUrlFetchService, mockFavIconFinder);

        AccountService accountService = new AccountServiceImpl(em, cryptoEngine);
        encryptionKey = accountService.createAccount(userId, "masterPassword").getEncryptionKey();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testGetCards() {
        CardDetails cardDetails1 = new CardDetails(null, "name", "login", "password", "url", "iconUrl", "note");
        CardDetails cardDetails2 = new CardDetails(null, "name2", "login2", "password2", "url2", "iconUrl2", "note2");
        Card card2 = impl.createCard(userId, cardDetails2, encryptionKey);
        Card card1 = impl.createCard(userId, cardDetails1, encryptionKey);
        List<CardBasicInformation> cards = impl.getCards(userId, encryptionKey);
        assertEquals(2, cards.size());
        assertEquals(card1.getId(), cards.get(0).getId());
        assertEquals(cardDetails1.getName(), cards.get(0).getName());
        assertEquals(cardDetails1.getIconUrl(), cards.get(0).getIconUrl());
        assertEquals(card2.getId(), cards.get(1).getId());
        assertEquals(cardDetails2.getName(), cards.get(1).getName());
        assertEquals(cardDetails2.getIconUrl(), cards.get(1).getIconUrl());
    }

    @Test
    public void testGetCardsWhenNoCards() {
        assertTrue(impl.getCards(userId, encryptionKey).isEmpty());
    }

    @Test
    public void testCardExists() {
        assertFalse(impl.cardExists(userId, "name", null, encryptionKey));
        CardDetails cardDetails1 = new CardDetails(null, "name", "login", "password", "url", "iconUrl", "note");
        Card card1 = impl.createCard(userId, cardDetails1, encryptionKey);
        assertTrue(impl.cardExists(userId, "name", null, encryptionKey));
        assertFalse(impl.cardExists(userId, "name", card1.getId(), encryptionKey));
    }

    @Test
    public void testCreateCard() {
        CardDetails cardDetails = new CardDetails(null, "name", "login", "password", "url", "iconUrl", "note");
        Card card = impl.createCard(userId, cardDetails, encryptionKey);
        assertEquals(userId, card.getAccount().getUserId());
        assertEquals(1, card.getAccount().getCards().size());
        assertEquals(cardDetails.getIconUrl(),
                     cryptoEngine.decryptString(card.getIconUrl(),
                                                encryptionKey,
                                                card.getInitializationVector()));
        assertEquals(cardDetails.getLogin(),
                     cryptoEngine.decryptString(card.getLogin(),
                                                encryptionKey,
                                                card.getInitializationVector()));
        assertEquals(cardDetails.getName(),
                     cryptoEngine.decryptString(card.getName(),
                                                encryptionKey,
                                                card.getInitializationVector()));
        assertEquals(cardDetails.getPassword(),
                     cryptoEngine.decryptString(card.getPassword(),
                                                encryptionKey,
                                                card.getInitializationVector()));
        assertEquals(cardDetails.getUrl(),
                     cryptoEngine.decryptString(card.getUrl(),
                                                encryptionKey,
                                                card.getInitializationVector()));
        assertEquals(cardDetails.getNote(),
                     cryptoEngine.decryptString(card.getNote(),
                                                encryptionKey,
                                                card.getInitializationVector()));
    }

    @Test
    public void testModifyCard() {
        CardDetails cardDetails = new CardDetails(null, "name", "login", "password", "url", "iconUrl", "note");
        Card card = impl.createCard(userId, cardDetails, encryptionKey);
        CardDetails cardDetails2 = new CardDetails(card.getId(), "name2", "login2", "password2", "url2", "iconUrl2", "note2");
        Card card2 = impl.modifyCard(cardDetails2, encryptionKey);
        assertEquals(card2.getId(), card.getId());
        CardDetails result = impl.getCardDetails(card.getId(), encryptionKey);
        assertEquals(cardDetails2.getIconUrl(), result.getIconUrl());
        assertEquals(cardDetails2.getLogin(), result.getLogin());
        assertEquals(cardDetails2.getName(), result.getName());
        assertEquals(cardDetails2.getPassword(), result.getPassword());
        assertEquals(cardDetails2.getUrl(), result.getUrl());
        assertEquals(cardDetails2.getNote(), result.getNote());
    }

    @Test
    public void testGetCardDetails() {
        assertNull(impl.getCardDetails("cardId", encryptionKey));

        CardDetails cardDetails = new CardDetails(null, "name", "login", "password", "url", "iconUrl", "note");
        Card card = impl.createCard(userId, cardDetails, encryptionKey);
        CardDetails result = impl.getCardDetails(card.getId(), encryptionKey);
        assertEquals(cardDetails.getIconUrl(), result.getIconUrl());
        assertEquals(cardDetails.getLogin(), result.getLogin());
        assertEquals(cardDetails.getName(), result.getName());
        assertEquals(cardDetails.getPassword(), result.getPassword());
        assertEquals(cardDetails.getUrl(), result.getUrl());
        assertEquals(cardDetails.getNote(), result.getNote());
    }

    @Test
    public void testDeleteCard() {
        CardDetails cardDetails = new CardDetails(null, "name", "login", "password", "url", "iconUrl", "note");
        Card card = impl.createCard(userId, cardDetails, encryptionKey);
        assertNotNull(impl.getCardDetails(card.getId(), encryptionKey));
        impl.deleteCard(card.getId());
        assertNull(impl.getCardDetails(card.getId(), encryptionKey));
    }

    @Test
    public void testFindFavIconUrl() throws Exception {
        String html = "html";
        HTTPResponse mockResponse = createMock(HTTPResponse.class);
        expect(mockResponse.getFinalUrl()).andReturn(null);
        expect(mockResponse.getContent()).andStubReturn(html.getBytes());

        String urlAsString = "http://my.domain.com/foo/index.html";
        URL url = new URL(urlAsString);
        expect(mockUrlFetchService.fetch(url)).andReturn(mockResponse);

        String iconUrl = "http://my.domain.com/ico.png";
        expect(mockFavIconFinder.findFavIcon(EasyMock.<InputSource>notNull(), eq(url))).andReturn(iconUrl);

        replay(mockUrlFetchService, mockResponse, mockFavIconFinder);

        String result = impl.findFavIconUrl(urlAsString);

        assertEquals(iconUrl, result);
        verify(mockUrlFetchService, mockResponse, mockFavIconFinder);
    }

    @Test
    public void testFindFavIconUrlWhenRedirectOccurs() throws Exception {
        String html = "html";
        HTTPResponse mockResponse = createMock(HTTPResponse.class);
        URL finalUrl = new URL("http://www.my.domain.com/foo/index.html");
        expect(mockResponse.getFinalUrl()).andStubReturn(finalUrl);
        expect(mockResponse.getContent()).andStubReturn(html.getBytes());

        String urlAsString = "http://my.domain.com/foo/index.html";
        URL url = new URL(urlAsString);
        expect(mockUrlFetchService.fetch(url)).andReturn(mockResponse);

        String iconUrl = "http://www.my.domain.com/ico.png";
        expect(mockFavIconFinder.findFavIcon(EasyMock.<InputSource>notNull(), eq(finalUrl))).andReturn(iconUrl);

        replay(mockUrlFetchService, mockResponse, mockFavIconFinder);

        String result = impl.findFavIconUrl(urlAsString);

        assertEquals(iconUrl, result);
        verify(mockUrlFetchService, mockResponse, mockFavIconFinder);
    }

    @Test
    public void testFindFavIconUrlWhenNotFoundByFinder() throws Exception {
        String html = "html";
        HTTPResponse mockResponse = createMock(HTTPResponse.class);
        expect(mockResponse.getFinalUrl()).andReturn(null);
        expect(mockResponse.getContent()).andStubReturn(html.getBytes());

        String urlAsString = "http://my.domain.com/foo/index.html";
        URL url = new URL(urlAsString);
        expect(mockUrlFetchService.fetch(url)).andReturn(mockResponse);

        expect(mockFavIconFinder.findFavIcon(EasyMock.<InputSource>notNull(), eq(url))).andReturn(null);

        HTTPResponse mockIconResponse = createMock(HTTPResponse.class);
        expect(mockIconResponse.getResponseCode()).andStubReturn(200);
        expect(mockUrlFetchService.fetch(new URL("http://my.domain.com/favicon.ico"))).andReturn(mockIconResponse);

        replay(mockUrlFetchService, mockResponse, mockFavIconFinder, mockIconResponse);

        String result = impl.findFavIconUrl(urlAsString);

        assertEquals("http://my.domain.com/favicon.ico", result);
        verify(mockUrlFetchService, mockResponse, mockFavIconFinder, mockIconResponse);
    }

    @Test(expected = FavIconException.class)
    public void testFindFavIconUrlWhenBadUrlProtocol() throws Exception {
        String urlAsString = "ftp://my.domain.com/foo/index.html";
        impl.findFavIconUrl(urlAsString);
    }

    @Test(expected = FavIconException.class)
    public void testFindFavIconUrlWhenBadUrl() throws Exception {
        String urlAsString = "http://my.domain com foo index.html";
        impl.findFavIconUrl(urlAsString);
    }

    @Test
    public void testFindFavIconUrlWhenNoDefaultFavIcon() throws Exception {
        String html = "html";
        HTTPResponse mockResponse = createMock(HTTPResponse.class);
        expect(mockResponse.getFinalUrl()).andStubReturn(null);
        expect(mockResponse.getContent()).andStubReturn(html.getBytes());

        String urlAsString = "http://my.domain.com/foo/index.html";
        URL url = new URL(urlAsString);
        expect(mockUrlFetchService.fetch(url)).andReturn(mockResponse);

        expect(mockFavIconFinder.findFavIcon(EasyMock.<InputSource>notNull(), eq(url))).andReturn(null);

        HTTPResponse mockIconResponse = createMock(HTTPResponse.class);
        expect(mockIconResponse.getResponseCode()).andStubReturn(404);
        expect(mockUrlFetchService.fetch(new URL("http://my.domain.com/favicon.ico"))).andReturn(mockIconResponse);

        replay(mockUrlFetchService, mockResponse, mockFavIconFinder, mockIconResponse);

        String result = impl.findFavIconUrl(urlAsString);

        assertNull(result);
        verify(mockUrlFetchService, mockResponse, mockFavIconFinder, mockIconResponse);
    }
}
