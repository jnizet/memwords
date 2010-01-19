package com.googlecode.memwords.domain;

import java.io.Serializable;

import javax.crypto.SecretKey;

/**
 * Basic user information that is stored in the session once authentication
 * or account creation is done
 * @author JB
 */
@SuppressWarnings("serial")
public final class UserInformation implements Serializable {
    private String userId;
    private SecretKey encryptionKey;
    
    public UserInformation(String userId, SecretKey encryptionKey) {
        this.userId = userId;
        this.encryptionKey = encryptionKey;
    }

    public String getUserId() {
        return userId;
    }

    public SecretKey getEncryptionKey() {
        return encryptionKey;
    }
}
