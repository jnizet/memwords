package com.googlecode.memwords.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public final class CardBasicInformation implements Serializable, Comparable<CardBasicInformation> {
	private String id;
	private String name;
	
	public CardBasicInformation(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(CardBasicInformation o) {
		return this.name.compareTo(o.name);
	}
}
