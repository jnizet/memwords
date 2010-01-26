package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.Locale;

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
    private Locale preferredLocale;

    public UserInformation(String userId, SecretKey encryptionKey, Locale preferredLocale) {
        this.userId = userId;
        this.encryptionKey = encryptionKey;
        this.preferredLocale = preferredLocale;
    }

    public String getUserId() {
        return userId;
    }

    public SecretKey getEncryptionKey() {
        return encryptionKey;
    }

    public Locale getPreferredLocale() {
        return preferredLocale;
    }

    public UserInformation withoutSecretKey() {
        return new UserInformation(this.userId, null, this.preferredLocale);
    }

    public UserInformation withSecretKey(SecretKey secretKey) {
        return new UserInformation(this.userId, secretKey, this.preferredLocale);
    }

    public UserInformation withPreferredLocale(Locale preferredLocale) {
        return new UserInformation(this.userId, this.encryptionKey, preferredLocale);
    }
}
