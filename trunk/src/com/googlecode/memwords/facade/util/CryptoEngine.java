package com.googlecode.memwords.facade.util;

import javax.crypto.SecretKey;

import com.google.inject.ImplementedBy;

@ImplementedBy(CryptoEngineImpl.class)
public interface CryptoEngine {
	/**
	 * Encrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to encrypt
	 * @param key the key which must contain 32 bytes
	 * @return the encrypted data
	 */
	byte[] encrypt(byte[] data, SecretKey key);
	
	/**
	 * Decrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to decrypt
	 * @param key the key which must contain 32 bytes
	 * @return the decrypted data
	 */
	byte[] decrypt(byte[] data, SecretKey key);
	
	/**
	 * Encrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to encrypt, as a String. It's transformed into a byte array 
	 * using the UTF-8 encoding
	 * @param key the key which must contain 32 bytes
	 * @return the encrypted data
	 */
	byte[] encryptString(String data, SecretKey key);
	
	/**
	 * Decrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to decrypt
	 * @param key the key which must contain 32 bytes
	 * @return the decrypted data, as a String. It's transformed into a String
	 * using the UTF-8 encoding
	 */
	String decryptString(byte[] data, SecretKey key);
}
