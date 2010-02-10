package com.googlecode.memwords.domain;

import java.io.Serializable;

/**
 * Basic information about a card (immutable value object), used to display a card list.
 * @author JB
 */
@SuppressWarnings("serial")
public final class CardBasicInformation implements Serializable {

    /**
     * The ID of the card
     */
    private String id;

    /**
     * The name of the card
     */
    private String name;

    /**
     * The URL of the icon of the card (<code>null</code> if it doesn't have one)
     */
    private String iconUrl;

    /**
     * Constructor
     * @param id the ID of the card (mandatory)
     * @param name the name of the card (mandatory)
     * @param iconUrl icon URL the URL of the icon of the vard (nullable)
     */
    public CardBasicInformation(String id, String name, String iconUrl) {
        if (id == null) {
            throw new IllegalArgumentException("id may not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name may not be null");
        }
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
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
     * Gets the URL of the icon of the card
     * @return the URL of the icon of the card, or <code>null</code> if it doesn't have one
     */
    public String getIconUrl() {
        return iconUrl;
    }
}
