package com.googlecode.memwords.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.datanucleus.jpa.annotations.Extension;

/**
 * Historic login persistent entity, containing information about a previous login
 * (date, user agent, IP address)
 * @author JB
 */
@Entity
public class HistoricLogin {

    /**
     * The auto-generated ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus",
               key = "gae.encoded-pk",
               value = "true")
    private String id;

    /**
     * The date of the login
     */
    @Basic(optional = false)
    private Date date;

    /**
     * The user agent string used to log in
     */
    @Basic(optional = false)
    private String userAgent;

    /**
     * The IP address of the machine used to log in
     */
    @Basic(optional = false)
    private String ip;

    /**
     * The account of this login
     */
    @ManyToOne(optional = false)
    private Account account;

    /**
     * Default constructor
     */
    public HistoricLogin() {
    }

    /**
     * Constructor
     * @param date the date and time of the login
     * @param userAgent the user agent string sent by the user when he logged in
     * @param ip the IP address of the user when he logged in
     */
    public HistoricLogin(Date date, String userAgent, String ip) {
        if (date != null) {
            this.date = new Date(date.getTime());
        }
        this.userAgent = userAgent;
        this.ip = ip;
    }

    /**
     * Gets the ID of the historic login
     * @return the ID of the historic login
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the historic login. For tests only: IDs are auto-generated
     * @param id the new ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the date of the historic login
     * @return a defensive copy of the date of the historic login
     */
    public Date getDate() {
        return new Date(date.getTime());
    }

    /**
     * Sets the date of the historic login
     * @param date the new date
     */
    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    /**
     * Gets the user agent of the historic login
     * @return the user agent of the historic login
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Sets the user agent of the historic login
     * @param userAgent the new user agent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Gets the IP address of the historic login
     * @return the IP address of the historic login
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the IP address of the historic login
     * @param ip the new IP address
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Gets the account of the historic login
     * @return the account of the historic login
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account of the historic login. This method is called by {@link Account#addHistoricLogin(HistoricLogin)}
     * @param account the new account.
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
