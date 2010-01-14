package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * An account, which is a Google account, and containing a master password hashed twice 
 * (to log in), and a secret key, encrypted with a key generated from the Google user ID and the 
 * master password hashed once. This secret key is used to encrypt sensitive information
 * in the database.
 * @author JB
 */
@SuppressWarnings("serial")
@Entity
public class Account implements Serializable {
	
	/**
	 * The user ID, which is also the primary key of the account.
	 */
	@Id
	private String userId;
	
	/**
	 * The persisted master password (which is in fact the concatenation of the user ID and
	 * master password, hashed twice)
	 */
	private byte[] masterPassword;
	
	/**
	 * The encrypted secret key
	 */
	private byte[] encryptedSecretKey;

	/**
	 * The authentication infos linked to this account
	 */
	@OneToMany(mappedBy = "account")
	private List<Card> cards = new LinkedList<Card>();
	
	public Account() {
	}
	
	public Account(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public byte[] getMasterPassword() {
		return masterPassword;
	}

	public void setMasterPassword(byte[] masterPassword) {
		this.masterPassword = masterPassword;
	}

	public byte[] getEncryptedSecretKey() {
		return encryptedSecretKey;
	}

	public void setEncryptedSecretKey(byte[] encryptedSecretKey) {
		this.encryptedSecretKey = encryptedSecretKey;
	}
	
	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}
	
	public void addCard(Card card) {
		card.setAccount(this);
		this.cards.add(card);
	}
}
