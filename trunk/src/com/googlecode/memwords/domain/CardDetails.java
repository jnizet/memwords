package com.googlecode.memwords.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public final class CardDetails implements Serializable {
	private String id;
	private String name;
	private String login;
	private String password;
	private String url;
	private String iconUrl;
	
	public CardDetails(String id, 
			           String name, 
			           String login, 
			           String password, 
			           String url, 
			           String iconUrl) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.url = url;
		this.iconUrl = iconUrl;
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
}
