package com.googlecode.memwords.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.datanucleus.jpa.annotations.Extension;

/**
 * Entity containing information about a previous login
 * (date, user agent, IP)
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
    private Date date;

    /**
     * The user agent string used to log in
     */
    private String userAgent;

    /**
     * The IP address of the machine used to log in
     */
    private String ip;

    /**
     * The account of this login
     */
    @ManyToOne(optional = false)
    private Account account;

    public HistoricLogin() {
    }

    public HistoricLogin(Date date, String userAgent, String ip) {
        if (date != null) {
            this.date = new Date(date.getTime());
        }
        this.userAgent = userAgent;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
