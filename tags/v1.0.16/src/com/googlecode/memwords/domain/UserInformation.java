package com.googlecode.memwords.domain;

import java.io.Serializable;

import javax.crypto.SecretKey;

/**
 * Basic user information that is stored in the session once authentication
 * or account creation is done (immutable value object)
 * @author JB
 */
@SuppressWarnings("serial")
public final class UserInformation implements Serializable {

    /**
     * The user ID
     */
    private String userId;

    /**
     * The secret key of the account, used to decrypt the information in the account and its cards
     */
    private SecretKey encryptionKey;

    /**
     * The preferences of the user
     */
    private Preferences preferences;

    /**
     * Constructor
     * @param userId the user ID (mandatory)
     * @param encryptionKey the secret key of the account, used to decrypt the information in
     * the account and its cards (nullable in order not to store the encryption key in the session)
     * @param preferences the preferences(mandatory)
     */
    public UserInformation(String userId,
                           SecretKey encryptionKey,
                           Preferences preferences) {
        if (userId == null) {
            throw new IllegalArgumentException("userID may not be null");
        }
        if (preferences == null) {
            throw new IllegalArgumentException("userID may not be null");
        }
        this.userId = userId;
        this.encryptionKey = encryptionKey;
        this.preferences = preferences;
    }

    /**
     * Gets the user ID
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the secret key, used to decrypt the information in the account and its cards
     * @return the secret key
     */
    public SecretKey getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Gets the preferences of the user
     * @return the preferences of the user
     */
    public Preferences getPreferences() {
        return preferences;
    }

    /**
     * Creates a copy of this instance with the secret key set to <code>null</code>
     * @return a copy of this instance with the secret key set to <code>null</code>
     */
    public UserInformation withoutSecretKey() {
        return new UserInformation(this.userId,
                                   null,
                                   this.preferences);
    }

    /**
     * Creates a copy of this instance with a different secret key
     * @param secretKey the new secret key
     * @return a copy of this instance with the given secret key
     */
    public UserInformation withSecretKey(SecretKey secretKey) {
        return new UserInformation(this.userId,
                                   secretKey,
                                   this.preferences);
    }

    /**
     * Creates a copy of this instance with a different set of preferences
     * @param preferences the new preferences
     * @return a copy of this instance with the given preferences
     */
    public UserInformation withPreferences(Preferences preferences) {
        return new UserInformation(this.userId,
                                   this.encryptionKey,
                                   preferences);
    }
}
