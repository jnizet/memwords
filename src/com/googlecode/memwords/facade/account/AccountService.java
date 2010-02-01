package com.googlecode.memwords.facade.account;

import javax.crypto.SecretKey;

import com.google.inject.ImplementedBy;
import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.Preferences;
import com.googlecode.memwords.domain.UserInformation;


/**
 * Facade service for accounts
 * @author JB
 */
@ImplementedBy(AccountServiceImpl.class)
public interface AccountService {
    /**
     * Gets the account with the given userId
     * @param userId the userId for which the account must be returned
     * @return the account with the given user ID, or <code>null</code> if no
     * account exists for the given user ID
     */
    Account getAccount(String userId);

    /**
     * Creates an account for the given user ID, using the given master password.
     * @param userId the ID of the user for which an account must be created
     * @param masterPassword the master password of the account
     * @return the user information, containing the encryption key generated for the account
     */
    UserInformation createAccount(String userId, String masterPassword);

    /**
     * Verifies the master password for the given user ID, and returns the decrypted
     * secret key associated to the account
     * @param userId the ID of the user to authenticate
     * @param masterPassword the master password associated to the account
     * @return the user information, containing the secret key associated to the account
     */
    UserInformation login(String userId, String masterPassword);

    /**
     * Checks that the given password is the right one for the given user ID
     * @param userId the user ID
     * @param masterPassword the password to check
     * @return <code>true</code> if the password is the right one, <code>false</code>
     * otherwise
     */
    boolean checkPassword(String userId, String masterPassword);

    /**
     * Changes the master password for the given user ID
     * @param userId the user ID
     * @param newPassword the new password
     * @param secretKey the encryption key of the account
     */
    void changePassword(String userId, String newPassword, SecretKey secretKey);

    /**
     * Tests if an account with the given userId exists or not
     */
    boolean accountExists(String userId);

    /**
     * Changes the preferences of the account with the given user ID
     * @param userId the user ID
     * @param locale the new preferences
     */
    void changePreferences(String userId, Preferences preferences);

    /**
     * Destroys the account with the given user ID
     * @param userId the user ID
     */
    void destroyAccount(String userId);
}
