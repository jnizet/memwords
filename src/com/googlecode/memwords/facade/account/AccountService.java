package com.googlecode.memwords.facade.account;

import javax.crypto.SecretKey;

import com.google.inject.ImplementedBy;
import com.googlecode.memwords.domain.Account;


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
	public Account getAccount(String userId);
	
	/**
	 * Creates an account for the given user ID, using the given master password.
	 * @param userId the ID of the user for which an account must be created
	 * @param masterPassword the master password of the account
	 * @return the encryption key generated for the account
	 */
	public SecretKey createAccount(String userId, String masterPassword);

	/**
	 * Verifies the master password for the given user ID, and returns the decrypted
	 * secret key associated to the account
	 * @param userId the ID of the user to authenticate
	 * @param masterPassword the master password associated to the account
	 * @return the secret key associated to the account
	 */
	public SecretKey login(String userId, String masterPassword);
	
	/**
	 * Tests if an account with the given userId exists or not
	 */
	public boolean accountExists(String userId);
}
