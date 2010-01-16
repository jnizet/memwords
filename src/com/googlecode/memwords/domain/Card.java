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
	 * The name of the web site or application
	 */
	private String name;
	
	/**
	 * The URL of the web site
	 */
	private String url;
	
	/**
	 * The description of the web site or application
	 */
	private String description;
	
	/**
	 * The password of the web site or application, encrypted using the secret key stored in 
	 * the account
	 */
	private String encryptedPassword;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
