package com.googlecode.memwords.facade.account;

import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.Card;
import com.googlecode.memwords.domain.Preferences;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.util.CryptoEngine;

/**
 * Implementation of {@link AccountService}
 * @author JB
 */
@Singleton
public class AccountServiceImpl implements AccountService {

    private EntityManager em;
    private CryptoEngine cryptoEngine;

    @Inject
    public AccountServiceImpl(EntityManager em, CryptoEngine cryptoEngine) {
        this.em = em;
        this.cryptoEngine = cryptoEngine;
    }

    @Override
    public Account getAccount(String userId) {
        return em.find(Account.class, userId);
    }

    @Override
    public UserInformation createAccount(String userId, String masterPassword) {
        SecretKey secretKey = cryptoEngine.generateEncryptionKey();
        byte[] persistentPassword = buildPersistentPassword(userId, masterPassword);
        SecretKey wrappingKey = buildWrappingKey(userId, masterPassword);
        byte[] iv = cryptoEngine.buildInitializationVector(wrappingKey.getEncoded());
        byte[] encryptedSecretKey = cryptoEngine.encrypt(secretKey.getEncoded(),
                                                         wrappingKey,
                                                         iv);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Account account = new Account(userId);
            account.setMasterPassword(persistentPassword);
            account.setEncryptedSecretKey(encryptedSecretKey);
            em.persist(account);
            tx.commit();
            return new UserInformation(account.getUserId(),
                                       secretKey,
                                       new Preferences(account.getPreferredLocale(),
                                                       account.getPreferredTimeZone(),
                                                       account.isPasswordsUnmasked()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public UserInformation login(String userId, String masterPassword) {
        Account account = getAccount(userId);
        if (account == null) {
            return null;
        }
        byte[] persistentPassword = buildPersistentPassword(userId, masterPassword);
        if (!Arrays.equals(persistentPassword, account.getMasterPassword())) {
            return null;
        }
        SecretKey wrappingKey = buildWrappingKey(userId, masterPassword);
        byte[] iv = cryptoEngine.buildInitializationVector(wrappingKey.getEncoded());
        byte[] encryptionKeyAsBytes = cryptoEngine.decrypt(account.getEncryptedSecretKey(),
                                                           wrappingKey,
                                                           iv);
        SecretKey secretKey = cryptoEngine.bytesToSecretKey(encryptionKeyAsBytes);
        return new UserInformation(account.getUserId(),
                                   secretKey,
                                   new Preferences(account.getPreferredLocale(),
                                                   account.getPreferredTimeZone(),
                                                   account.isPasswordsUnmasked()));
    }

    @Override
    public boolean checkPassword(String userId, String masterPassword) {
        Account account = getAccount(userId);
        if (account == null) {
            return false;
        }
        byte[] persistentPassword = buildPersistentPassword(userId, masterPassword);
        return Arrays.equals(persistentPassword, account.getMasterPassword());
    }

    @Override
    public void changePassword(String userId, String newPassword, SecretKey secretKey) {
        byte[] persistentPassword = buildPersistentPassword(userId, newPassword);
        SecretKey wrappingKey = buildWrappingKey(userId, newPassword);
        byte[] iv = cryptoEngine.buildInitializationVector(wrappingKey.getEncoded());
        byte[] encryptedSecretKey = cryptoEngine.encrypt(secretKey.getEncoded(), wrappingKey, iv);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Account account = getAccount(userId);
            account.setMasterPassword(persistentPassword);
            account.setEncryptedSecretKey(encryptedSecretKey);
            tx.commit();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public boolean accountExists(String userId) {
        return getAccount(userId) != null;
    }

    @Override
    public void changePreferences(String userId, Preferences preferences) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Account account = getAccount(userId);
            account.setPreferredLocale(preferences.getLocale());
            account.setPreferredTimeZone(preferences.getTimeZone());
            account.setPasswordsUnmasked(preferences.isPasswordsUnmasked());
            tx.commit();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void destroyAccount(String userId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Account account = getAccount(userId);

            for (Card card : account.getCards()) {
                em.remove(card);
            }

            em.remove(account);
            tx.commit();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    /**
     * Builds the key used to encrypt the randomly generated encryption key associated to the
     * account
     */
    protected SecretKey buildWrappingKey(String userId, String masterPassword) {
        String s = userId + masterPassword;
        byte[] b = cryptoEngine.stringToBytes(s);
        byte[] keyAsBytes = cryptoEngine.hash(b);
        return cryptoEngine.bytesToSecretKey(keyAsBytes);
    }

    /**
     * Builds the persistent password from the master password and the user ID (which
     * is used as salt in order to avoid having the same result with identical passwords)
     */
    protected byte[] buildPersistentPassword(String userId, String masterPassword) {
        String s = userId + masterPassword;
        byte[] b = cryptoEngine.stringToBytes(s);
        return cryptoEngine.hash(cryptoEngine.hash(b));
    }


}
