package com.googlecode.memwords.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.datanucleus.jpa.annotations.Extension;

/**
 * A card, linked to an account, containing authentication information, usually for a web site, 
 * or for an application
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
			   key="gae.encoded-pk", 
			   value="true")
	private String id;
	
	/**
	 * The name of the web site or application (encrypted)
	 */
	private byte[] name;
	
	/**
	 * The URL of the web site (encrypted)
	 */
	private byte[] url;
	
	/**
	 * The URL of the icon of the card (encrypted)
	 */
	private byte[] iconUrl;
	
	/**
	 * The description of the web site or application (encrypted)
	 */
	private byte[] description;
	
	/**
	 * The login of the web site or application (encrypted)
	 */
	private byte[] login;
	
	/**
	 * The password of the web site or application (encrypted)
	 */
	private byte[] password;
	
	/**
	 * The account of the authentication info
	 */
	@ManyToOne(optional = false)
	private Account account;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getName() {
		return name;
	}

	public void setName(byte[] name) {
		this.name = name;
	}

	public byte[] getUrl() {
		return url;
	}

	public void setUrl(byte[] url) {
		this.url = url;
	}

	public byte[] getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(byte[] iconUrl) {
		this.iconUrl = iconUrl;
	}

	public byte[] getDescription() {
		return description;
	}

	public void setDescription(byte[] description) {
		this.description = description;
	}

	public byte[] getLogin() {
		return login;
	}

	public void setLogin(byte[] login) {
		this.login = login;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
