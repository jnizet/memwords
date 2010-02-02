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
    private Preferences preferences;

    public UserInformation(String userId,
                           SecretKey encryptionKey,
                           Preferences preferences) {
        this.userId = userId;
        this.encryptionKey = encryptionKey;
        this.preferences = preferences;
    }

    public String getUserId() {
        return userId;
    }

    public SecretKey getEncryptionKey() {
        return encryptionKey;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public UserInformation withoutSecretKey() {
        return new UserInformation(this.userId,
                                   null,
                                   this.preferences);
    }

    public UserInformation withSecretKey(SecretKey secretKey) {
        return new UserInformation(this.userId,
                                   secretKey,
                                   this.preferences);
    }

    public UserInformation withPreferences(Preferences preferences) {
        return new UserInformation(this.userId,
                                   this.encryptionKey,
                                   preferences);
    }
}
