package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang.LocaleUtils;

/**
 * An account, identified by a user ID and containing a master password hashed twice
 * (to log in), and a secret key, encrypted with a key generated from the user ID and the
 * master password hashed once. This secret key is used to encrypt sensitive information
 * in the database.
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
    private byte[] masterPassword;

    /**
     * The encrypted secret key
     */
    private byte[] encryptedSecretKey;

    /**
     * The preferred locale
     */
    private String preferredLocale;

    /**
     * The preferred time zone (ID)
     */
    private String preferredTimeZone;

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

    public Locale getPreferredLocale() {
        return LocaleUtils.toLocale(this.preferredLocale);
    }

    public void setPreferredLocale(Locale preferredLocale) {
        if (preferredLocale == null) {
            this.preferredLocale = null;
        }
        else {
            this.preferredLocale = preferredLocale.toString();
        }
    }

    public TimeZone getPreferredTimeZone() {
        if (this.preferredTimeZone == null) {
            return null;
        }
        return TimeZone.getTimeZone(this.preferredTimeZone);
    }

    public void setPreferredTimeZone(TimeZone timeZone) {
        if (timeZone == null) {
            this.preferredTimeZone = null;
        }
        else {
            this.preferredTimeZone = timeZone.getID();
        }
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public void addCard(Card card) {
        card.setAccount(this);
        this.cards.add(card);
    }

    public List<HistoricLogin> getHistoricLogins() {
        return Collections.unmodifiableList(historicLogins);
    }

    /**
     * Adds a historic login at the beginning of the list
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
