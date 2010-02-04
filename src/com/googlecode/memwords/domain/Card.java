package com.googlecode.memwords.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.datanucleus.jpa.annotations.Extension;

/**
 * Card persistent entity, linked to an account, containing authentication information,
 * usually for a web site, or for an application.
 * @author JB
 */
@SuppressWarnings("serial")
@Entity
public class Card implements Serializable {

    /**
     * The auto-generated ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus",
               key = "gae.encoded-pk",
               value = "true")
    private String id;

    /**
     * The initialization vector (IV) used to encrypt/decrypt information stored in this card.
     * The use of a different IV for each card makes sure that if two cards have the same password,
     * the encrypted value stored in the database will be different.
     */
    @Basic(optional = false)
    private byte[] initializationVector;

    /**
     * The name of the web site or application (encrypted by the secret key of the account)
     */
    @Basic(optional = false)
    private byte[] name;

    /**
     * The URL of the web site (encrypted by the secret key of the account)
     */
    @Basic(optional = true)
    private byte[] url;

    /**
     * The URL of the icon of the card (encrypted by the secret key of the account)
     */
    @Basic(optional = true)
    private byte[] iconUrl;

    /**
     * The note attached to the card (encrypted by the secret key of the account)
     */
    @Basic(optional = true)
    private byte[] note;

    /**
     * The login of the web site or application (encrypted by the secret key of the account)
     */
    @Basic(optional = false)
    private byte[] login;

    /**
     * The password of the web site or application (encrypted by the secret key of the account)
     */
    @Basic(optional = false)
    private byte[] password;

    /**
     * The account of the card
     */
    @ManyToOne(optional = false)
    private Account account;

    /**
     * Gets the ID of the card
     * @return the ID of the card
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the card (for tests only: card IDs are auto-generated)
     * @param id the new ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the initialization vector (IV) used to encrypt/decrypt information stored in the card.
     * The use of a different IV for each card makes sure that if two cards have the same password,
     * the encrypted value stored in the database will be different.
     * @return the initialization vector (IV) used to encrypt/decrypt information stored in the card
     */
    public byte[] getInitializationVector() {
        return initializationVector;
    }

    /**
     * Sets the new initialization vector (IV), used to encrypt/decrypt information stored in the card.
     * @param initializationVector new new initialization vector
     */
    public void setInitializationVector(byte[] initializationVector) {
        this.initializationVector = initializationVector;
    }

    /**
     * Gets the name of the card, encrypted by the secret key of the account
     * @return the encrypted name of the card
     */
    public byte[] getName() {
        return name;
    }

    /**
     * Sets the encrypted name of the card
     * @param name the new encrypted name
     */
    public void setName(byte[] name) {
        this.name = name;
    }

    /**
     * Gets the URL of the web site, encrypted by the secret key of the account
     * @return the encrypted name of the card
     * @return the encrypted URL of the card, or <code>null</code> if the card doesn't have one
     */
    public byte[] getUrl() {
        return url;
    }

    /**
     * Sets the encrypted URL of the card
     * @param url the new encrypted URL, or <code>null</code> if the card doesn't have one
     */
    public void setUrl(byte[] url) {
        this.url = url;
    }

    /**
     * Gets the URL of the icon of the card, encrypted by the secret key of the account
     * @return the encrypted icon URL, or <code>null</code> if the card doesn't have one
     */
    public byte[] getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets the encrypted URL of the icon of the card
     * @param iconUrl the new encrypted icon URL, or <code>null</code> if the card doesn't have one
     */
    public void setIconUrl(byte[] iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Gets the note associated to the card, encrypted by the secret key of the account
     * @return the encrypted note, or <code>null</code> if the card doesn't have one
     */
    public byte[] getNote() {
        return note;
    }

    /**
     * Sets the encrypted note of the card
     * @param note the new note, or <code>null</code> if the card doesn't have one
     */
    public void setNote(byte[] note) {
        this.note = note;
    }

    /**
     * Gets the login of the card, encrypted by the secret key of the account
     * @return the encrypted login
     */
    public byte[] getLogin() {
        return login;
    }

    /**
     * Sets the encrypted login of the card
     * @param login the new encrypted login
     */
    public void setLogin(byte[] login) {
        this.login = login;
    }

    /**
     * Gets the password of the card, encrypted by the secret key of the account
     * @return the encrypted password
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * Sets the encrypted password of the card
     * @param password the new encrypted password
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * Gets the account of the card
     * @return the account of the card
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account of the card. Called by {@link Account#addCard(Card)}
     * @param account the account of the card
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
