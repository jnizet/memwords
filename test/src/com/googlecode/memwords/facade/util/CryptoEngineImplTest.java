package com.googlecode.memwords.facade.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.crypto.SecretKey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CryptoEngineImplTest {

    private CryptoEngineImpl engine;
    
    @Before
    public void setUp() throws Exception {
        engine = new CryptoEngineImpl();
    }

    @After
    public void tearDown() throws Exception {
        engine = null;
    }

    @Test
    public void testEncryptAndDecrypt() {
        byte[] data = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        SecretKey key = engine.generateEncryptionKey();
        assertEquals(CryptoEngineImpl.KEY_SIZE, key.getEncoded().length);
        assertEquals("AES", key.getAlgorithm());
        byte[] iv = engine.generateInitializationVector();
        assertEquals(CryptoEngineImpl.IV_SIZE, iv.length);
        byte[] encrypted = engine.encrypt(data, key, iv);
        assertFalse(Arrays.equals(data, encrypted));
        byte[] decrypted = engine.decrypt(encrypted, key, iv);
        assertTrue(Arrays.equals(data, decrypted));
        
        SecretKey key2 = engine.generateEncryptionKey();
        assertFalse(Arrays.equals(key.getEncoded(), key2.getEncoded()));
        byte[] iv2 = engine.generateInitializationVector();
        assertFalse(Arrays.equals(iv, iv2));
        byte[] encrypted2 = engine.encrypt(data, key, iv2);
        assertFalse(Arrays.equals(encrypted, encrypted2));
    }

    @Test
    public void testEncryptStringAndDecryptString() {
        String data = "Hello world";
        SecretKey key = engine.generateEncryptionKey();
        byte[] iv = engine.generateInitializationVector();
        byte[] encrypted = engine.encryptString(data, key, iv);
        String decrypted = engine.decryptString(encrypted, key, iv);
        assertEquals(data, decrypted);
    }

    @Test
    public void testHash() {
        byte[] data = "Hello world".getBytes();
        byte[] hashed = engine.hash(data);
        assertFalse(Arrays.equals(data, hashed));
        assertEquals(32, hashed.length);
    }

    @Test
    public void testBytesToSecretKey() {
        byte[] bytes16 = new byte[16];
        for (byte i = 0; i < bytes16.length; i++) {
            bytes16[i] = i;
        }
        SecretKey key = engine.bytesToSecretKey(bytes16);
        assertTrue(Arrays.equals(bytes16, key.getEncoded()));
        
        byte[] bytes32 = new byte[32];
        for (byte i = 0; i < bytes32.length; i++) {
            bytes32[i] = i;
        }
        key = engine.bytesToSecretKey(bytes32);
        assertTrue(Arrays.equals(bytes16, key.getEncoded()));
        
        try {
            byte[] bytes15 = new byte[15];
            engine.bytesToSecretKey(bytes15);
            fail("expected an IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testBuildInitializationVector() {
        byte[] bytes16 = new byte[16];
        for (byte i = 0; i < bytes16.length; i++) {
            bytes16[i] = i;
        }
        byte[] iv = engine.buildInitializationVector(bytes16);
        assertTrue(Arrays.equals(bytes16, iv));
        
        byte[] bytes32 = new byte[32];
        for (byte i = 0; i < bytes32.length; i++) {
            bytes32[i] = i;
        }
        iv = engine.buildInitializationVector(bytes32);
        assertTrue(Arrays.equals(bytes16, iv));
        
        try {
            byte[] bytes15 = new byte[15];
            engine.buildInitializationVector(bytes15);
            fail("expected an IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }

}
