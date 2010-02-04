package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang.LocaleUtils;

/**
 * Account persistent entity, identified by a user ID and containing a master password hashed twice
 * (to log in), and a secret key, encrypted with a key generated from the user ID and the
 * master password hashed once. This secret key is used to encrypt sensitive information
 * in the database.
 * The account also holds the user preferences.
 * @author JB
 */
@SuppressWarnings("serial")
@Entity
public class Account implements Serializable {

    /**
     * The max number of historic logins per account
     */
    public static final int MAX_HISTORIC_LOGIN_COUNT = 5;

    /**
     * The user ID, which is also the primary key of the account.
     */
    @Id
    private String userId;

    /**
     * The persisted master password (which is in fact the concatenation of the user ID and
     * master password, hashed twice)
     */
    @Basic(optional = false)
    private byte[] masterPassword;

    /**
     * The encrypted secret key
     */
    @Basic(optional = false)
    private byte[] encryptedSecretKey;

    /**
     * The preferred locale
     */
    @Basic(optional = true)
    private String preferredLocale;

    /**
     * The preferred time zone (ID)
     */
    @Basic(optional = true)
    private String preferredTimeZone;

    /**
     * Preferences regarding the default masking of passwords
     * (nullable for easy migration)
     */
    @Basic(optional = true)
    private Boolean passwordsUnmasked = false;

    /**
     * The authentication infos linked to this account
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Card> cards = new LinkedList<Card>();

    /**
     * The historic logins of this account, sorted by date, in descending order (latest first)
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @OrderBy("date DESC")
    private List<HistoricLogin> historicLogins = new ArrayList<HistoricLogin>();

    /**
     * Default constructor
     */
    public Account() {
    }

    /**
     * Constructor initializing the ID
     * @param userId the user ID (PK) or this account
     */
    public Account(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the user ID
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID. This method should only be used on non-persistent instances.
     * @param userId the new user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the master password, encrypted by the secret key
     * @return the master password, encrypted by the secret key
     */
    public byte[] getMasterPassword() {
        return masterPassword;
    }

    /**
     * Sets the encrypted master password
     * @param masterPassword the new encrypted master password
     */
    public void setMasterPassword(byte[] masterPassword) {
        this.masterPassword = masterPassword;
    }

    /**
     * Gets the secret key associated to the account, encrypted with the user ID and password hashed once
     * @return the encrypted secret key
     */
    public byte[] getEncryptedSecretKey() {
        return encryptedSecretKey;
    }

    /**
     * Sets the encrypted secret key associated to the account
     * @param encryptedSecretKey the new encrypted secret key
     */
    public void setEncryptedSecretKey(byte[] encryptedSecretKey) {
        this.encryptedSecretKey = encryptedSecretKey;
    }

    /**
     * Gets the preferred locale of the account.
     * @return the preferred locale of the account, or <code>null</code> if the account
     * doesn't have one (automatic detection).
     */
    public Locale getPreferredLocale() {
        return LocaleUtils.toLocale(this.preferredLocale);
    }

    /**
     * Sets the preferred locale of the account.
     * @param preferredLocale the new preferred locale, or <code>null</code> if the locale
     * should be auto-detected for this account.
     */
    public void setPreferredLocale(Locale preferredLocale) {
        if (preferredLocale == null) {
            this.preferredLocale = null;
        }
        else {
            this.preferredLocale = preferredLocale.toString();
        }
    }

    /**
     * Gets the preferred time zone of the account.
     * @return the preferred time zone, or <code>null</code> if the account doesn't have one
     * (in which case the default time zone should be used)
     */
    public TimeZone getPreferredTimeZone() {
        if (this.preferredTimeZone == null) {
            return null;
        }
        return TimeZone.getTimeZone(this.preferredTimeZone);
    }

    /**
     * Sets the preferred time zone of the account
     * @param timeZone the new time zone, or <code>null</code> if the default time zone
     * should be used.
     */
    public void setPreferredTimeZone(TimeZone timeZone) {
        if (timeZone == null) {
            this.preferredTimeZone = null;
        }
        else {
            this.preferredTimeZone = timeZone.getID();
        }
    }

    /**
     * Gets the preference for the default masking of passwords
     * @return <code>true</code> if passwords should be unmasked by default, <code>false</code>
     * otherwise.
     */
    public boolean isPasswordsUnmasked() {
        if (passwordsUnmasked == null) {
            return false;
        }
        return passwordsUnmasked;
    }

    /**
     * Sets the preference for the default masking of passwords
     * @param passwordsUnmasked <code>true</code> if passwords should be unmasked by default,
     * <code>false</code> otherwise
     */
    public void setPasswordsUnmasked(boolean passwordsUnmasked) {
        this.passwordsUnmasked = passwordsUnmasked;
    }

    /**
     * Gets the unmodifiable list of cards attached to this account. The returned cards are not sorted
     * @return an unmodifiable list of cards
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Adds a card the the list of cards of this account, and sets the account of the given card.
     * @param card the new card to add to the list. This card is supposed to be new, i.e. not to
     * be attached to any account yet.
     */
    public void addCard(Card card) {
        card.setAccount(this);
        this.cards.add(card);
    }

    /**
     * Gets the unmodifiable list of historic logins of the account, sorted by date, in descending
     * order (latest first)
     * @return an unmodifiable list of historic logins
     */
    public List<HistoricLogin> getHistoricLogins() {
        return Collections.unmodifiableList(historicLogins);
    }

    /**
     * Adds a historic login at the beginning of the list, and sets the account of the given
     * historic login. The historic login is supposed to be new (i.e. not to
     * be attached to any account yet) and have a date bigger (more recent) than all the logins
     * of the list, in order to respect the order in the list.
     * @return <code>true</code> if the number of logins after the
     * addition is larger than {@link #MAX_HISTORIC_LOGIN_COUNT MAX_HISTORIC_LOGIN_COUNT}
     */
    public boolean addHistoricLogin(HistoricLogin historicLogin) {
        historicLogin.setAccount(this);
        historicLogins.add(0, historicLogin);
        return historicLogins.size() > MAX_HISTORIC_LOGIN_COUNT;
    }

    /**
     * Removes the historic logins at the end of the list until the size of the list
     * is equal to {@link #MAX_HISTORIC_LOGIN_COUNT MAX_HISTORIC_LOGIN_COUNT}
     */
    public void removeOldHistoricLogins() {
        while (historicLogins.size() > MAX_HISTORIC_LOGIN_COUNT) {
            historicLogins.remove(historicLogins.size() - 1);
        }
    }
}
