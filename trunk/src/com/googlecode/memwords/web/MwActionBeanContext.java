package com.googlecode.memwords.web;

import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.jsp.jstl.core.Config;

import net.sourceforge.stripes.action.ActionBeanContext;

import org.apache.commons.codec.binary.Base64;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Inject;
import com.googlecode.memwords.domain.MwConstants;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.loginhistory.LoginHistoryService;
import com.googlecode.memwords.facade.util.CryptoEngine;

/**
 * The subclass of <code>ActionBeanContext</code> used in the application
 * @author JB
 */
public class MwActionBeanContext extends ActionBeanContext {

    /**
     * The session attribute used to store the user information (without secret key)
     */
    public static final String USER_INFORMATION_SESSION_ATTRIBUTE = "userInformation";

    /**
     * The request attribute used to store the user information (with secret key)
     */
    public static final String USER_INFORMATION_REQUEST_ATTRIBUTE = "userInformation";

    /**
     * The request attribute used to store the URL asked by the user, but intercepted by
     * the authentication interceptor
     */
    public static final String REQUESTED_URL_REQUEST_ATTRIBUTE = "requestedUrl";

    /**
     * The name of the cookie containing the secret key
     */
    public static final String SECRET_KEY_COOKIE_NAME = "userInformation";

    /**
     * The session attribute containing the temporary key used to encrypt the user's secret key
     * in the cookie
     */
    public static final String COOKIE_ENCRYPTION_KEY_SESSION_ATTRIBUTE = "cookieEncryptionKey";

    /**
     * The cryptographic engine
     */
    private CryptoEngine cryptoEngine;

    /**
     * The login history service
     */
    private LoginHistoryService loginHistoryService;

    /**
     * Sets the cryptographic engine
     * @param cryptoEngine the cryptographic engine
     */
    @Inject
    public void setCryptoEngine(CryptoEngine cryptoEngine) {
        this.cryptoEngine = cryptoEngine;
    }

    /**
     * Sets the login history service
     * @param loginHistoryService the login history service
     */
    @Inject
    public void setLoginHistoryService(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    /**
     * Adds a login to the login history, then stores the user information
     * with secret key in the request, the user information without secret
     * key in the session, and the secret key in a cookie. The secret key is not
     * stored in the session so that it's not written in database.
     * @param userInformation the user information, with the secret key
     */
    public void login(UserInformation userInformation) {
        loginHistoryService.addLogin(userInformation.getUserId(), getUserAgent(), getRequest().getRemoteAddr());
        setUserInformation(userInformation);

        SecretKey cookieEncryptionKey = cryptoEngine.generateEncryptionKey();
        getRequest().getSession().setAttribute(COOKIE_ENCRYPTION_KEY_SESSION_ATTRIBUTE,
                                               cookieEncryptionKey.getEncoded());
        byte[] encryptedSecretKey =
            cryptoEngine.encrypt(userInformation.getEncryptionKey().getEncoded(),
                                 cookieEncryptionKey,
                                 cryptoEngine.buildInitializationVector(cookieEncryptionKey.getEncoded()));

        // important : no end of line, else the cookie contains control
        // characters, and it doesn't work
        Base64 base64 = new Base64(-1);
        Cookie cookie = new Cookie(SECRET_KEY_COOKIE_NAME,
                                   base64.encodeToString(encryptedSecretKey));
        cookie.setMaxAge(-1);
        cookie.setPath(getRequest().getContextPath() + "/");
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            cookie.setSecure(true);
        }
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

    /**
     * Gets the user information stored in the request, with secret key
     * @return the user information, with secret key
     */
    public UserInformation getUserInformation() {
        return (UserInformation) getRequest().getAttribute(USER_INFORMATION_REQUEST_ATTRIBUTE);
    }

    /**
     * Sets the user information (after a login or a change in the preferences). It stores the user
     * information with secret key in the request, the user information without secret key in the session,
     * the locale in the session where the locale picker can find it, and the time zone in the session
     * where the JSTL tags can find it
     * @param info the new user information, with secret key
     */
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
                byte[] encryptedSecretKey = base64.decode(secretKeyCookie.getValue());

                byte[] cookieEncryptionKeyAsBytes =
                    (byte[]) getRequest().getSession().getAttribute(COOKIE_ENCRYPTION_KEY_SESSION_ATTRIBUTE);
                SecretKey cookieEncryptionKey = cryptoEngine.bytesToSecretKey(cookieEncryptionKeyAsBytes);
                byte[] secretKeyAsBytes =
                    cryptoEngine.decrypt(encryptedSecretKey,
                                         cookieEncryptionKey,
                                         cryptoEngine.buildInitializationVector(cookieEncryptionKeyAsBytes));
                SecretKey secretKey = cryptoEngine.bytesToSecretKey(secretKeyAsBytes);
                UserInformation userInformation = infoWithoutSecretKey.withSecretKey(secretKey);
                setUserInformation(userInformation);
            }
        }
    }

    /**
     * Method called by the authentication interceptor, when a GET URL is asked but
     * needs an authentication before. The login action bean then finds the requested URL
     * in the context and places it in a hidden field
     * @param url the requested URL
     */
    public void setRequestedUrl(String url) {
        getRequest().setAttribute(REQUESTED_URL_REQUEST_ATTRIBUTE, url);
    }

    /**
     * Gets the requested URL initialized by the authentication interceptor, if any.
     * @return the requested URL initialized by the authentication interceptor.
     */
    public String getRequestedUrl() {
        return (String) getRequest().getAttribute(REQUESTED_URL_REQUEST_ATTRIBUTE);
    }

    /**
     * Tests if the user is logged in, i.e. if a user information is in the
     * request
     * @return <code>true</code> if the user is logged in, <code>false</code> otherwise
     */
    public boolean isLoggedIn() {
        return getUserInformation() != null;
    }

    /**
     * Gets the user agent of the current request.
     * @return the user agent of the current request
     */
    public String getUserAgent() {
        return getRequest().getHeader("User-Agent");
    }

    /**
     * Gets the locale to use for this request. If set in the user preferences, returns this one. Else,
     * returns the default one.
     * @return the locale to use for the current request
     */
    @Override
    public Locale getLocale() {
        Locale result = getUserInformation().getPreferences().getLocale();
        if (result == null) {
            result = super.getLocale();
        }
        return result;
    }

    /**
     * Gets the time zone to use for this request. If set in the user preferences, returns this one.
     * Else, returns GMT.
     * @return the time zone to use for this request
     */
    public TimeZone getTimeZone() {
        UserInformation userInformation = getUserInformation();
        if (userInformation != null) {
            return userInformation.getPreferences().getTimeZone();
        }
        else {
            return TimeZone.getTimeZone(MwConstants.GMT);
        }
    }
}
