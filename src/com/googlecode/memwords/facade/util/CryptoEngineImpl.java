package com.googlecode.memwords.facade.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.google.inject.Singleton;

@Singleton
public class CryptoEngineImpl implements CryptoEngine {

	private SecureRandom secureRandom;
	
	public CryptoEngineImpl() {
		try {
			this.secureRandom = SecureRandom.getInstance("SHA1PRNG");
		} 
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] encrypt(byte[] data, SecretKey key) {
		return crypt(data, key, Cipher.ENCRYPT_MODE);
	}

	@Override
	public byte[] decrypt(byte[] data, SecretKey key) {
		return crypt(data, key, Cipher.DECRYPT_MODE);
	}
	
	@Override
	public byte[] encryptString(String data, SecretKey key) {
		return crypt(stringToBytes(data), key, Cipher.ENCRYPT_MODE);
	}

	@Override
	public String decryptString(byte[] data, SecretKey key) {
		return bytesToString(crypt(data, key, Cipher.DECRYPT_MODE));
	}
	
	@Override
	public byte[] hash(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] result = digest.digest(data);
			return result;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public SecretKey bytesToSecretKey(byte[] keyAsBytes) {
		SecretKeySpec keySpec = new SecretKeySpec(keyAsBytes, "AES");
		return keySpec;
	}
	
	@Override
	public SecretKey generateEncryptionKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256, secureRandom);
			return keyGenerator.generateKey();
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected byte[] crypt(byte[] data, SecretKey key, int cryptMode) {
		try {
			if (key == null) {
				throw new IllegalArgumentException("key must not be null");
			}
			if (data == null) {
				return null;
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

	protected byte[] stringToBytes(String s) {
		if (s == null) {
			return null;
		}
		try {
			return s.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String bytesToString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		try {
			return new String(bytes, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
