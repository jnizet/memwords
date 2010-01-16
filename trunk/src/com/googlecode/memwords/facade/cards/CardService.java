package com.googlecode.memwords.facade.cards;

import java.util.List;

import javax.crypto.SecretKey;

import com.google.inject.ImplementedBy;
import com.googlecode.memwords.domain.Card;
import com.googlecode.memwords.domain.CardBasicInformation;
import com.googlecode.memwords.domain.CardDetails;

/**
 * Facade services for cards
 * @author JB
 */
@ImplementedBy(CardServiceImpl.class)
public interface CardService {
	/**
	 * Returns the list of cards of the given account, 
	 * sorted by name.
	 * @param userId the ID of the account
	 * @return the list of cards
	 */
	List<CardBasicInformation> getCards(String userId);

	/**
	 * Tests if a card already exists for the given account
	 * @param userId the ID of the account
	 * @param name the name of the card
	 * @return <code>true</code> if a card exists, <code>false</code> otherwise
	 */
	boolean cardExists(String userId, String name);

	/**
	 * Creates a card 
	 * @param userId the account of the card
	 * @param name the name of the card
	 * @param url the URL of the card
	 * @return the created card
	 */
	Card createCard(String userId, String name, String url);
	
	/**
	 * Gets the details of a card, decrypted
	 * @param cardId the ID of the card
	 * @param encryptionKey the key used to decrypt the encrypted information of the card
	 * @return the card details
	 */
	CardDetails getCardDetails(String cardId, SecretKey encyptionKey);

	/**
	 * Deletes the card with the given ID
	 * @param cardId the ID of the card to delete
	 */
	void deleteCard(String cardId);
}
