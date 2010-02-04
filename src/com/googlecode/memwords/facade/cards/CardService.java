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
     * Returns the list of cards of the given account, sorted by name.
     * @param userId the ID of the account
     * @param encryptionKey the key used to decrypt the encrypted information of the cards
     * @return the list of cards, sorted by name
     */
    List<CardBasicInformation> getCards(String userId, SecretKey encryptionKey);

    /**
     * Tests if a card already exists for the given account
     * @param userId the ID of the account
     * @param name the name of the card
     * @param cardId if not <code>null</code>, then only searches in cards which don't have this ID
     * @param encryptionKey the key used to decrypt the encrypted information of the cards
     * @return <code>true</code> if a card exists, <code>false</code> otherwise
     */
    boolean cardExists(String userId, String name, String cardId, SecretKey encryptionKey);

    /**
     * Creates a new card
     * @param userId the ID of the account of the card to create
     * @param cardDetails the details of the card (without ID)
     * @param encryptionKey the encryption key to use to encrypt the information in the card
     * @return the created card
     */
    Card createCard(String userId, CardDetails cardDetails, SecretKey encryptionKey);

    /**
     * Modifies a card
     * @param cardDetails the details of the card (with ID)
     * @param encryptionKey the encryption key to use to encrypt the information in the card
     * @param modifyPassword <code>true</code> if the password must be modified, <code>false</code>
     * otherwise. If <code>false</code>, the password field of the <code>cardDetails</code> is ignored.
     * @return the modified card
     */
    Card modifyCard(CardDetails cardDetails, SecretKey encryptionKey, boolean modifyPassword);


    /**
     * Gets the details of a card, decrypted
     * @param cardId the ID of the card
     * @param encryptionKey the key used to decrypt the encrypted information of the card
     * @return the card details
     */
    CardDetails getCardDetails(String cardId, SecretKey encryptionKey);

    /**
     * Deletes the card with the given ID
     * @param cardId the ID of the card to delete
     */
    void deleteCard(String cardId);

    /**
     * Finds the absolute URL of the favIcon associated to the given web site
     * @param url the URL (http or https) of a web site
     * @return the absolute URL of the fav icon associated to the web site, or <code>null</code> if no
     * fav icon found
     * @throws FavIconException if there was an exception while fetching the fav icon URL
     */
    String findFavIconUrl(String url) throws FavIconException;
}
