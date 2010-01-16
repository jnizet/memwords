package com.googlecode.memwords.facade.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import com.google.inject.Singleton;

@Singleton
public class CryptoEngineImpl implements CryptoEngine {

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

	protected byte[] stringToBytes(String s) {
		try {
			return s.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String bytesToString(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
