package com.googlecode.memwords.domain;

import java.io.Serializable;

/**
 * The details of a card (unencrypted)
 * @author JB
 */
@SuppressWarnings("serial")
public final class CardDetails implements Serializable {
    private String id;
    private String name;
    private String login;
    private String password;
    private String url;
    private String iconUrl;
    private String note;

    public CardDetails(String id,
                       String name,
                       String login,
                       String password,
                       String url,
                       String iconUrl,
                       String note) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.url = url;
        this.iconUrl = iconUrl;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getNote() {
        return note;
    }
}
