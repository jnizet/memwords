package com.googlecode.memwords.facade.account;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.Card;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.domain.MwConstants;
import com.googlecode.memwords.domain.PasswordGenerationPreferences;
import com.googlecode.memwords.domain.Preferences;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.facade.cards.CardServiceImpl;
import com.googlecode.memwords.facade.util.CryptoEngine;
import com.googlecode.memwords.facade.util.CryptoEngineImpl;
import com.googlecode.memwords.test.util.GAETestCase;

public class AccountServiceImplTest extends GAETestCase {

    private EntityManager em;
    private CryptoEngine mockCryptoEngine;
    private AccountServiceImpl impl;
    private AccountServiceImpl implWithRealCryptoEngine;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        em = createEntityManager();
        mockCryptoEngine = createMock(CryptoEngine.class);
        impl = new AccountServiceImpl(em, mockCryptoEngine);
        implWithRealCryptoEngine = new AccountServiceImpl(em, new CryptoEngineImpl());
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testGetAccount() {
        String userId = "userId";
        implWithRealCryptoEngine.createAccount(userId, "masterPassword");

        Account account = impl.getAccount(userId);
        assertEquals(userId, account.getUserId());
        assertNull(impl.getAccount("userId2"));
    }

    @Test
    public void testCreateAccount() {
        String userId = "userId";
        String masterPassword = "masterPassword";

        byte[] encryptionKey = new byte[] {1, 2};
        SecretKey secretKey = new SecretKeySpec(encryptionKey, "AES");
        byte[] notHashedUserIdAndPassword = new byte[] {2, 3};
        byte[] hashedOnceUserIdAndPassword = new byte[] {3, 4};
        byte[] hashedTwiceUserIdAndPassword = new byte[] {4, 5};
        byte[] iv = new byte[] {5, 6};
        SecretKey wrappingKey = new SecretKeySpec(new byte[] {5, 6}, "AES");
        byte[] encryptedSecretKey = new byte[] {6, 7};

        expect(mockCryptoEngine.generateEncryptionKey()).andReturn(secretKey);
        expect(mockCryptoEngine.stringToBytes(userId + masterPassword)).andStubReturn(notHashedUserIdAndPassword);
        expect(mockCryptoEngine.hash(aryEq(notHashedUserIdAndPassword))).andStubReturn(hashedOnceUserIdAndPassword);
        expect(mockCryptoEngine.hash(aryEq(hashedOnceUserIdAndPassword))).andStubReturn(hashedTwiceUserIdAndPassword);
        expect(mockCryptoEngine.bytesToSecretKey(aryEq(hashedOnceUserIdAndPassword))).andReturn(wrappingKey);
        expect(mockCryptoEngine.buildInitializationVector(aryEq(wrappingKey.getEncoded()))).andReturn(iv);
        expect(mockCryptoEngine.encrypt(aryEq(encryptionKey), same(wrappingKey), aryEq(iv))).andReturn(encryptedSecretKey);

        replay(mockCryptoEngine);

        impl.createAccount(userId, masterPassword);

        Account account = em.find(Account.class, userId);
        assertEquals(userId, account.getUserId());
        assertTrue(account.getCards().isEmpty());
        assertTrue(Arrays.equals(hashedTwiceUserIdAndPassword, account.getMasterPassword()));
        assertTrue(Arrays.equals(encryptedSecretKey, account.getEncryptedSecretKey()));
        assertFalse(account.isPasswordsUnmasked());
        verify(mockCryptoEngine);
    }

    @Test
    public void testLogin() {
        String userId = "userId";
        String masterPassword = "masterPassword";

        implWithRealCryptoEngine.createAccount(userId, masterPassword);

        byte[] encryptionKey = new byte[] {1, 2};
        SecretKey secretKey = new SecretKeySpec(encryptionKey, "AES");
        byte[] notHashedUserIdAndPassword = new byte[] {2, 3};
        byte[] hashedOnceUserIdAndPassword = new byte[] {3, 4};
        byte[] iv = new byte[] {5, 6};
        SecretKey wrappingKey = new SecretKeySpec(new byte[] {5, 6}, "AES");

        Account account = em.find(Account.class, userId);

        expect(mockCryptoEngine.stringToBytes(userId + masterPassword)).andStubReturn(notHashedUserIdAndPassword);
        expect(mockCryptoEngine.hash(aryEq(notHashedUserIdAndPassword))).andStubReturn(hashedOnceUserIdAndPassword);
        expect(mockCryptoEngine.hash(aryEq(hashedOnceUserIdAndPassword))).andStubReturn(account.getMasterPassword());
        expect(mockCryptoEngine.bytesToSecretKey(aryEq(hashedOnceUserIdAndPassword))).andReturn(wrappingKey);
        expect(mockCryptoEngine.buildInitializationVector(aryEq(wrappingKey.getEncoded()))).andReturn(iv);
        expect(mockCryptoEngine.decrypt(aryEq(account.getEncryptedSecretKey()), same(wrappingKey), aryEq(iv))).andReturn(encryptionKey);
        expect(mockCryptoEngine.bytesToSecretKey(encryptionKey)).andReturn(secretKey);

        replay(mockCryptoEngine);

        UserInformation loginUserInformation = impl.login(userId, masterPassword);
        assertSame(secretKey, loginUserInformation.getEncryptionKey());

        assertNotNull(implWithRealCryptoEngine.login(userId, masterPassword));
        assertNull(implWithRealCryptoEngine.login("userId2", masterPassword));
        assertNull(implWithRealCryptoEngine.login(userId, "masterPassword2"));
    }

    @Test
    public void testCheckPassword() {
        String userId = "userId";
        implWithRealCryptoEngine.createAccount(userId, "masterPassword");
        assertTrue(implWithRealCryptoEngine.checkPassword(userId, "masterPassword"));
        assertFalse(implWithRealCryptoEngine.checkPassword(userId, "masterPassword2"));
    }

    @Test
    public void testChangePassword() {
        String userId = "userId";
        implWithRealCryptoEngine.createAccount(userId, "masterPassword");
        SecretKey secretKeyBeforeChange =
            implWithRealCryptoEngine.login(userId, "masterPassword").getEncryptionKey();
        implWithRealCryptoEngine.changePassword(userId, "newPassword", secretKeyBeforeChange);
        SecretKey secretKeyAfterChange =
            implWithRealCryptoEngine.login(userId, "newPassword").getEncryptionKey();
        assertTrue(Arrays.equals(secretKeyBeforeChange.getEncoded(), secretKeyAfterChange.getEncoded()));
    }

    @Test
    public void testAccountExists() {
        String userId = "userId";
        implWithRealCryptoEngine.createAccount(userId, "masterPassword");
        assertTrue(implWithRealCryptoEngine.accountExists(userId));
        assertFalse(implWithRealCryptoEngine.accountExists("userId2"));
    }

    @Test
    public void testChangePreferences() {
        String userId = "userId";
        UserInformation userInfoBeforeChange = implWithRealCryptoEngine.createAccount(userId, "masterPassword");
        assertNull(userInfoBeforeChange.getPreferences().getLocale());
        assertEquals(TimeZone.getTimeZone(MwConstants.GMT), userInfoBeforeChange.getPreferences().getTimeZone());
        assertFalse(userInfoBeforeChange.getPreferences().isPasswordsUnmasked());
        Locale newLocale = new Locale("fr", "FR");
        TimeZone newTimeZone = TimeZone.getTimeZone("Europe/Paris");
        implWithRealCryptoEngine.changePreferences(userId,
                                                   new Preferences(newLocale,
                                                                   newTimeZone,
                                                                   true,
                                                                   new PasswordGenerationPreferences(4, false, true, false, true)));
        UserInformation userInfoAfterChange =
            implWithRealCryptoEngine.login(userId, "masterPassword");
        assertEquals(newLocale, userInfoAfterChange.getPreferences().getLocale());
        assertEquals(newTimeZone, userInfoAfterChange.getPreferences().getTimeZone());
        assertTrue(userInfoAfterChange.getPreferences().isPasswordsUnmasked());
        assertEquals(4, userInfoAfterChange.getPreferences().getPasswordGenerationPreferences().getLength());
        assertFalse(userInfoAfterChange.getPreferences().getPasswordGenerationPreferences().isLowerCaseLettersIncluded());
        assertTrue(userInfoAfterChange.getPreferences().getPasswordGenerationPreferences().isUpperCaseLettersIncluded());
        assertFalse(userInfoAfterChange.getPreferences().getPasswordGenerationPreferences().isDigitsIncluded());
        assertTrue(userInfoAfterChange.getPreferences().getPasswordGenerationPreferences().isSpecialCharactersIncluded());
    }

    @Test
    public void testDestroyAccount() {
        String userId = "userId";
        UserInformation userInfo = implWithRealCryptoEngine.createAccount(userId, "masterPassword");

        CardService cardService = new CardServiceImpl(em, new CryptoEngineImpl(), null, null);
        Card card =
            cardService.createCard(userId,
                                   new CardDetails("id",
                                                   "name",
                                                   "login",
                                                   "password",
                                                   null,
                                                   null,
                                                   null),
                                   userInfo.getEncryptionKey());

        implWithRealCryptoEngine.destroyAccount(userId);
        Account account = impl.getAccount(userId);
        assertNull(account);
        card = em.find(Card.class, card.getId());
        assertNull(card);
    }
}
