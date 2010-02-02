package com.googlecode.memwords.web;

import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.jsp.jstl.core.Config;

import net.sourceforge.stripes.action.ActionBeanContext;

import org.apache.commons.codec.binary.Base64;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.MwConstants;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.loginhistory.LoginHistoryService;
import com.googlecode.memwords.facade.util.CryptoEngine;

/**
 * The subclass of ActionBeranContext used in this application
 *
 * @author JB
 */
public class MwActionBeanContext extends ActionBeanContext {

    public static final String USER_INFORMATION_SESSION_ATTRIBUTE = "userInformation";
    public static final String USER_INFORMATION_REQUEST_ATTRIBUTE = "userInformation";
    public static final String REQUESTED_URL_REQUEST_ATTRIBUTE = "requestedUrl";
    public static final String SECRET_KEY_COOKIE_NAME = "userInformation";

    private CryptoEngine cryptoEngine;
    private LoginHistoryService loginHistoryService;

    @Inject
    public void setCryptoEngine(CryptoEngine cryptoEngine) {
        this.cryptoEngine = cryptoEngine;
    }

    @Inject
    public void setLoginHistoryService(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    /**
     * Adds a login to the login history, then stores the user information
     * without secret key in the request, the user information without secret
     * key in the session, and the secret key in a cookie. The secret key is not
     * stored in the session so that it's not written in database.
     *
     * @param userInformation
     *            the user information, with the secret key
     */
    public void login(UserInformation userInformation) {
        loginHistoryService.addLogin(userInformation.getUserId(), getUserAgent(), getRequest().getRemoteAddr());
        setUserInformation(userInformation);

        // important : no end of line, else the cookie contains control
        // characters,
        // and it doesn't work
        Base64 base64 = new Base64(-1);
        Cookie cookie = new Cookie(SECRET_KEY_COOKIE_NAME, base64.encodeToString(userInformation.getEncryptionKey()
            .getEncoded()));
        cookie.setMaxAge(-1);
        cookie.setPath(getRequest().getContextPath() + "/");
        getRequest().getSession().setAttribute(USER_INFORMATION_SESSION_ATTRIBUTE, userInformation.withoutSecretKey());
        getResponse().addCookie(cookie);
    }

    /**
     * Removes the user information from the request and session, and removes
     * the secret key cookie
     */
    public void logout() {
        getRequest().removeAttribute(USER_INFORMATION_REQUEST_ATTRIBUTE);
        getRequest().getSession().removeAttribute(USER_INFORMATION_SESSION_ATTRIBUTE);
        Cookie cookie = new Cookie(SECRET_KEY_COOKIE_NAME, "reset");
        cookie.setMaxAge(0);
        cookie.setPath(getRequest().getContextPath() + "/");
        getResponse().addCookie(cookie);
    }

    public UserInformation getUserInformation() {
        return (UserInformation) getRequest().getAttribute(USER_INFORMATION_REQUEST_ATTRIBUTE);
    }

    public void setUserInformation(UserInformation info) {
        MwLocalePicker.setPreferredLocale(getRequest(), info.getPreferences().getLocale());
        getRequest().setAttribute(USER_INFORMATION_REQUEST_ATTRIBUTE, info);
        getRequest().getSession().setAttribute(USER_INFORMATION_SESSION_ATTRIBUTE, info.withoutSecretKey());
        Config.set(getRequest().getSession(), Config.FMT_TIME_ZONE, info.getPreferences().getTimeZone());
    }

    /**
     * Method called by the authentication interceptor. It fetches the user
     * information (without secret key from the session, then (if found) fetches
     * the secret key from the cookie, and builds a user information with secret
     * key that it stores in the request.
     */
    protected void loadUserInformation() {
        if (getUserInformation() != null) {
            return;
        }
        UserInformation infoWithoutSecretKey = (UserInformation) getRequest().getSession()
            .getAttribute(USER_INFORMATION_SESSION_ATTRIBUTE);
        if (infoWithoutSecretKey != null) {
            Cookie secretKeyCookie = null;
            Cookie[] allCookies = getRequest().getCookies();
            for (Cookie c : allCookies) {
                if (SECRET_KEY_COOKIE_NAME.equals(c.getName())) {
                    secretKeyCookie = c;
                    break;
                }
            }
            if (secretKeyCookie != null) {
                // important : no end of line, else the cookie contains control
                // characters,
                // and it doesn't work
                Base64 base64 = new Base64(-1);
                byte[] secretKeyAsBytes = base64.decode(secretKeyCookie.getValue());
                SecretKey secretKey = cryptoEngine.bytesToSecretKey(secretKeyAsBytes);
                UserInformation userInformation = infoWithoutSecretKey.withSecretKey(secretKey);
                setUserInformation(userInformation);
            }
        }
    }

    public void setRequestedUrl(String url) {
        getRequest().setAttribute(REQUESTED_URL_REQUEST_ATTRIBUTE, url);
    }

    public String getRequestedUrl() {
        return (String) getRequest().getAttribute(REQUESTED_URL_REQUEST_ATTRIBUTE);
    }

    /**
     * Tests if the user is logged in, i.e. if a user information is in the
     * request
     */
    public boolean isLoggedIn() {
        return getUserInformation() != null;
    }

    public String getUserAgent() {
        return getRequest().getHeader("User-Agent");
    }

    @Override
    public Locale getLocale() {
        Locale result = getUserInformation().getPreferences().getLocale();
        if (result == null) {
            result = super.getLocale();
        }
        return result;
    }

    public TimeZone getTimeZone() {
        UserInformation userInformation = getUserInformation();
        if (userInformation != null && userInformation.getPreferences().getTimeZone() != null) {
            return userInformation.getPreferences().getTimeZone();
        }
        else {
            return TimeZone.getTimeZone(MwConstants.GMT);
        }
    }
}
