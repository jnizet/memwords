package com.googlecode.memwords.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public final class CardDetails implements Serializable {
	private String id;
	private String name;
	
	public CardDetails(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
