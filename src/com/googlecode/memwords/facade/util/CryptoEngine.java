package com.googlecode.memwords.facade.util;

import javax.crypto.SecretKey;

import com.google.inject.ImplementedBy;

@ImplementedBy(CryptoEngineImpl.class)
public interface CryptoEngine {
	/**
	 * Encrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 128-bits key
	 * @param data the data to encrypt
	 * @param key the key which must contain 16 bytes
	 * @param iv the initialization vector which must contain 16 bytes
	 * @return the encrypted data or <code>null</code> if data is <code>null</code>
	 */
	byte[] encrypt(byte[] data, SecretKey key, byte[] iv);
	
	/**
	 * Decrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to decrypt
	 * @param key the key which must contain 16 bytes
	 * @param iv the initialization vector which must contain 16 bytes
	 * @return the decrypted data or <code>null</code> if data is <code>null</code>
	 */
	byte[] decrypt(byte[] data, SecretKey key, byte[] iv);
	
	/**
	 * Encrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to encrypt, as a String. It's transformed into a byte array 
	 * using the UTF-8 encoding
	 * @param key the key which must contain 16 bytes
	 * @param iv the initialization vector which must contain 16 bytes
	 * @return the encrypted data, or <code>null</code> if data is <code>null</code>
	 */
	byte[] encryptString(String data, SecretKey key, byte[] iv);
	
	/**
	 * Decrypts the given data, using the given key. The algorithm used is AES, with a 
	 * 256-bits key
	 * @param data the data to decrypt
	 * @param key the key which must contain 16 bytes
	 * @param iv the initialization vector which must contain 16 bytes
	 * @return the decrypted data as a String, or <code>null</code> if the data is <code>null</code>. 
	 * It's transformed into a String using the UTF-8 encoding
	 */
	String decryptString(byte[] data, SecretKey key, byte[] iv);
	
	/**
	 * Hashes the data using SHA-256, which results in a byte array containing 32 bytes
	 * @param data the data to hash
	 * @return the hashed data (32 bytes - 256 bits)
	 */
	byte[] hash(byte[] data);
	
	/**
	 * Transforms the raw byte array into a secret key for the AES (128 bits - 16 bytes) algorithm
	 * @param keyAsBytes the key bytes, which must contain at least 16 bytes (only the 16 first 
	 * bytes are kept)
	 * @return the key as a <code>SecretKey</code>
	 */
	SecretKey bytesToSecretKey(byte[] keyAsBytes);
	
	/**
	 * Generates a random encryption key for the AES (128 bits - 16 bytes) algorithm
	 */
	SecretKey generateEncryptionKey();
	
	/**
	 * Generates a random initialization vector for the AES algorithm (128 bits - 16 bytes)
	 */
	byte[] generateInitializationVector();
	
	/**
	 * Takes the first 16 bytes of the given byte array in order to use them as IV
	 * @param bytes the byte array, which must be at least 16 bytes long
	 */
	byte[] buildInitializationVector(byte[] bytes);
}
