package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

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
    private TimeZone preferredTimeZone;

    public UserInformation(String userId,
                           SecretKey encryptionKey,
                           Locale preferredLocale,
                           TimeZone preferredTimeZone) {
        this.userId = userId;
        this.encryptionKey = encryptionKey;
        this.preferredLocale = preferredLocale;
        this.preferredTimeZone = preferredTimeZone;
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

    public TimeZone getPreferredTimeZone() {
        return preferredTimeZone;
    }

    public UserInformation withoutSecretKey() {
        return new UserInformation(this.userId,
                                   null,
                                   this.preferredLocale,
                                   this.preferredTimeZone);
    }

    public UserInformation withSecretKey(SecretKey secretKey) {
        return new UserInformation(this.userId,
                                   secretKey,
                                   this.preferredLocale,
                                   this.preferredTimeZone);
    }

    public UserInformation withPreferredLocale(Locale preferredLocale) {
        return new UserInformation(this.userId,
                                   this.encryptionKey,
                                   preferredLocale,
                                   this.preferredTimeZone);
    }

    public UserInformation withPreferredTimeZone(TimeZone preferredTimeZone) {
        return new UserInformation(this.userId,
                                   this.encryptionKey,
                                   this.preferredLocale,
                                   preferredTimeZone);
    }
}
