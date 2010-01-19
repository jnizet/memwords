package com.googlecode.memwords.domain;

import java.io.Serializable;

/**
 * Basic information about a card, in  order to display a card list. Instances of this class
 * have a natural order : thee natural order of their name.
 * @author JB
 */
@SuppressWarnings("serial")
public final class CardBasicInformation implements Serializable, Comparable<CardBasicInformation> {
    private String id;
    private String name;
    private String iconUrl;
    
    public CardBasicInformation(String id, String name, String iconUrl) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
    
    @Override
    public int compareTo(CardBasicInformation o) {
        return this.name.compareTo(o.name);
    }
}
