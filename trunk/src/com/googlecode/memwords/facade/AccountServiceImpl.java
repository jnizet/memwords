package com.googlecode.memwords.facade;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.memwords.domain.Account;


@Singleton
public class AccountServiceImpl implements AccountService {

	private EntityManager em;
	
	@Inject
	public AccountServiceImpl(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Account getAccount(String userId) {
		return em.find(Account.class, userId);
	}
	
	@Override
	public SecretKey createAccount(String userId, String masterPassword, String sessionId) {
		SecretKey secretKey = generateEncryptionKey(userId, masterPassword, sessionId);
		byte[] persistentPassword = buildPersistentPassword(userId, masterPassword);
		SecretKey wrappingKey = buildWrappingKey(userId, masterPassword);
		byte[] encryptedSecretKey = encrypt(secretKey.getEncoded(), wrappingKey);
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Account account = new Account(userId);
			account.setMasterPassword(persistentPassword);
			account.setEncryptedSecretKey(encryptedSecretKey);
			em.persist(account);
			tx.commit();
			return secretKey;
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
	
	@Override
	public SecretKey login(String userId, String masterPassword) {
		Account account = getAccount(userId);
		if (account == null) {
			return null;
		}
		byte[] persistentPassword = buildPersistentPassword(userId, masterPassword);
		if (!Arrays.equals(persistentPassword, account.getMasterPassword())) {
			return null;
		}
		SecretKey wrappingKey = buildWrappingKey(userId, masterPassword);
		byte[] encryptionKeyAsBytes = decrypt(account.getEncryptedSecretKey(), wrappingKey);
		return bytesToSecretKey(encryptionKeyAsBytes);
	}
	
	/**
	 * Encrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to encrypt
	 * @param key the key which must contain 32 bytes
	 * @return the encrypted data
	 */
	protected byte[] encrypt(byte[] data, SecretKey key) {
		return crypt(data, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * Decrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to decrypt
	 * @param key the key which must contain 32 bytes
	 * @return the decrypted data
	 */
	protected byte[] decrypt(byte[] data, SecretKey key) {
		return crypt(data, key, Cipher.DECRYPT_MODE);
	}
	
	protected byte[] crypt(byte[] data, SecretKey key, int cryptMode) {
		try {
			if (data == null || key == null) {
				throw new IllegalArgumentException("data and key must not be null");
			}
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(cryptMode, key);
	
		    byte[] encrypted = cipher.doFinal(data);
		    return encrypted;
		}
		catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Hashes the data using SHA-256, which results in a byte array containing 32 bytes
	 * @param data the data to hash
	 * @return the hashed data (32 bytes - 256 bits)
	 */
	protected byte[] hash(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] result = digest.digest(data);
			return result;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Builds the key used to encrypt the randomly generated encryption key associated to the 
	 * account
	 */
	protected SecretKey buildWrappingKey(String userId, String masterPassword) {
		String s = userId + masterPassword;
		byte[] b = stringToBytes(s);
		byte[] keyAsBytes = hash(b);
		return bytesToSecretKey(keyAsBytes);
	}

	private SecretKey bytesToSecretKey(byte[] keyAsBytes) {
		SecretKeySpec keySpec = new SecretKeySpec(keyAsBytes, "AES");
		return keySpec;
	}
	
	/**
	 * Generates a random encryption key
	 */
	protected SecretKey generateEncryptionKey(String userId, 
			                            String masterPassword, 
			                            String sessionId) {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(stringToBytes(sessionId));
			secureRandom.setSeed(System.currentTimeMillis());
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256, secureRandom);
			return keyGenerator.generateKey();
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Builds the persistent password from the master password and the user ID (which 
	 * is used as salt in order to avoid having the same result with identical passwords)
	 */
	protected byte[] buildPersistentPassword(String userId, String masterPassword) {
		String s = userId + masterPassword;
		byte[] b = stringToBytes(s);
		return hash(hash(b));
	}
	
	protected byte[] stringToBytes(String s) {
		try {
			return s.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
