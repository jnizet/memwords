package com.googlecode.memwords.domain;

import java.io.Serializable;

/**
 * The details of a card (immutable value object, unencrypted)
 * @author JB
 */
@SuppressWarnings("serial")
public final class CardDetails implements Serializable {
    /**
     * The ID of the card
     */
    private String id;

    /**
     * The name of the card
     */
    private String name;

    /**
     * The login of the card
     */
    private String login;

    /**
     * The password of the card
     */
    private String password;

    /**
     * The URL of the card (<code>null</code> if it doesn't have one)
     */
    private String url;

    /**
     * The URL of the icon of the card (<code>null</code> if it doesn't have one)
     */
    private String iconUrl;

    /**
     * The note associated to the card (<code>null</code> if it doesn't have one)
     */
    private String note;

    /**
     * Constructor
     * @param id the ID of the card (nullable if the card details are not coming from a persistent card (creation))
     * @param name the name of the card (mandatory)
     * @param login the login of the card (mandatory)
     * @param password the password of the card (nullable if the card details are not coming from a persistent
     * card (modification) and the password must not be changed)
     * @param url the URL of the card (nullable)
     * @param iconUrl the URL of the icon of the card (nullable)
     * @param note the note associated to the card (nullable)
     */
    public CardDetails(String id,
                       String name,
                       String login,
                       String password,
                       String url,
                       String iconUrl,
                       String note) {
        if (name == null) {
            throw new IllegalArgumentException("name may not be null");
        }
        if (login == null) {
            throw new IllegalArgumentException("login may not be null");
        }
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.url = url;
        this.iconUrl = iconUrl;
        this.note = note;
    }

    /**
     * Gets the ID of the card
     * @return the ID of the card
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the card
     * @return the name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the login of the card
     * @return the login of the card
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets the password of the card
     * @return the password of the card
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the URL of the card
     * @return the URL of the card, or <code>null</code> if it doesn't have one
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the URL of the icon of card
     * @return the URL of the icon of the card, or <code>null</code> if it doesn't have one
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Gets the note associated to the card
     * @return the note associated to the card, or <code>null</code> if it doesn't have one
     */
    public String getNote() {
        return note;
    }

    /**
     * Gets the absolutized URL.
     * @see UrlUtils#absolutizeUrl(String)
     */
    public String getAbsolutizedUrl() {
        return UrlUtils.absolutizeUrl(this.url);
    }
}
