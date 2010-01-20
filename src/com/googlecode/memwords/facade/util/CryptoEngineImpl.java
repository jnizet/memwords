package com.googlecode.memwords.facade.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.inject.Singleton;
import com.googlecode.memwords.domain.ShouldNeverHappenException;

/**
 * Implementation of {@link CryptoEngine}
 * @author JB
 */
@Singleton
public class CryptoEngineImpl implements CryptoEngine {

    public static final int KEY_SIZE = 16;
    public static final int IV_SIZE = 16;

    private static final int KEY_SIZE_IN_BITS = KEY_SIZE * 8;
    private static final String ENCRYPTION_ALGORITHM = "AES";

    private SecureRandom secureRandom;

    public CryptoEngineImpl() {
        try {
            this.secureRandom = SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException e) {
            throw new ShouldNeverHappenException(e);
        }
    }

    @Override
    public byte[] encrypt(byte[] data, SecretKey key, byte[] iv) {
        return crypt(data, key, iv, Cipher.ENCRYPT_MODE);
    }

    @Override
    public byte[] decrypt(byte[] data, SecretKey key, byte[] iv) {
        return crypt(data, key, iv, Cipher.DECRYPT_MODE);
    }

    @Override
    public byte[] encryptString(String data, SecretKey key, byte[] iv) {
        return crypt(stringToBytes(data), key, iv, Cipher.ENCRYPT_MODE);
    }

    @Override
    public String decryptString(byte[] data, SecretKey key, byte[] iv) {
        return bytesToString(crypt(data, key, iv, Cipher.DECRYPT_MODE));
    }

    @Override
    public byte[] hash(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] result = digest.digest(data);
            return result;
        }
        catch (NoSuchAlgorithmException e) {
            throw new ShouldNeverHappenException(e);
        }
    }

    @Override
    public SecretKey bytesToSecretKey(byte[] keyAsBytes) {
        if (keyAsBytes.length < KEY_SIZE) {
            throw new IllegalArgumentException("the key bytes must be at least " + KEY_SIZE + " bytes long");
        }
        byte[] bytes = keyAsBytes;
        if (keyAsBytes.length > KEY_SIZE) {
            bytes = new byte[KEY_SIZE];
            System.arraycopy(keyAsBytes, 0, bytes, 0, KEY_SIZE);
        }
        SecretKeySpec keySpec = new SecretKeySpec(bytes, ENCRYPTION_ALGORITHM);
        return keySpec;
    }

    @Override
    public SecretKey generateEncryptionKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
            keyGenerator.init(KEY_SIZE_IN_BITS, secureRandom);
            return keyGenerator.generateKey();
        }
        catch (NoSuchAlgorithmException e) {
            throw new ShouldNeverHappenException(e);
        }
    }

    @Override
    public byte[] generateInitializationVector() {
        byte[] result = new byte[IV_SIZE];
        secureRandom.nextBytes(result);
        return result;
    }

    @Override
    public byte[] buildInitializationVector(byte[] bytes) {
        if (bytes.length < IV_SIZE) {
            throw new IllegalArgumentException("bytes must be at least " + IV_SIZE + " long");
        }
        byte[] result = new byte[IV_SIZE];
        System.arraycopy(bytes, 0, result, 0, IV_SIZE);
        return result;
    }

    @Override
    public byte[] stringToBytes(String s) {
        if (s == null) {
            return null;
        }
        try {
            return s.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new ShouldNeverHappenException(e);
        }
    }

    protected byte[] crypt(byte[] data, SecretKey key, byte[] iv, int cryptMode) {
        try {
            if (key == null) {
                throw new IllegalArgumentException("key must not be null");
            }
            if (key.getEncoded().length != KEY_SIZE) {
                throw new IllegalArgumentException("key is not of the right size (" + KEY_SIZE + ")");
            }
            if (iv.length != IV_SIZE) {
                throw new IllegalArgumentException("IV is not of the right size (" + IV_SIZE + ")");
            }
            if (data == null) {
                return null;
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(cryptMode, key, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(data);
            return encrypted;
        }
        catch (GeneralSecurityException e) {
            throw new ShouldNeverHappenException(e);
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
            throw new ShouldNeverHappenException(e);
        }
    }
}
