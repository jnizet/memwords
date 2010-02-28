package com.googlecode.memwords.web.account;

import java.io.IOException;
import java.text.DateFormat;
import java.util.TimeZone;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.HistoricLogin;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.facade.loginhistory.LoginHistoryService;
import com.googlecode.memwords.web.IndexActionBean;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.cards.CardsActionBean;
import com.googlecode.memwords.web.loginhistory.LoginHistoryActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to handle the login and logout
 * @author JB
 */
@HttpCache(allow = false)
public class LoginActionBean extends MwActionBean {

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * The login history service
     */
    private LoginHistoryService loginHistoryService;

    /**
     * Input field containing the user ID
     */
    @Validate(required = true)
    private String userId;

    /**
     * Input field containing the master password
     */
    @Validate(required = true)
    private String masterPassword;

    /**
     * Hidden input field containing the URLto redirect to after a successful login.
     * <code>null</code> if no specific page has been asked.
     */
    private String requestedUrl;

    /**
     * Constructor
     * @param accountService the account service
     * @param loginHistoryService the login history service
     */
    @Inject
    public LoginActionBean(AccountService accountService,
                           LoginHistoryService loginHistoryService) {
        this.accountService = accountService;
        this.loginHistoryService = loginHistoryService;
    }

    /**
     * Displays the login page
     * @return a forward resolution to the login page, containing a hidden field
     * with the requested URL if found in the context.
     * @see com.googlecode.memwords.web.MwActionBeanContext#getRequestedUrl()
     */
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        this.requestedUrl = getContext().getRequestedUrl();
        return new ForwardResolution("/account/login.jsp");
    }

    /**
     * Performs the login.
     * If the login succeeds, a success message is added containing information about the latest
     * historic login if any
     * @return a resolution to the source page if the login failed, with an error message;
     * a redirect resolution to the request URL if any and if the login succeeded; or a
     * redirect resolution to the cards page if the login succeeded and if no URL was requested.
     */
    public Resolution login() throws IOException {
        UserInformation userInformation =
            accountService.login(userId, masterPassword);
        if (userInformation == null) {
            getContext().getValidationErrors().addGlobalError(
                    new LocalizableError("loginFailed"));
            return getContext().getSourcePageResolution();
        }
        getContext().login(userInformation);

        HistoricLogin historicLogin =
            loginHistoryService.getLatestHistoricLogin(userInformation.getUserId());
        if (historicLogin != null) {
            TimeZone timeZone = getContext().getTimeZone();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, getContext().getLocale());
            dateFormat.setTimeZone(timeZone);
            DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM, getContext().getLocale());
            timeFormat.setTimeZone(timeZone);
            RedirectResolution loginHistoryResolution = new RedirectResolution(LoginHistoryActionBean.class);
            String loginHistoryUrl =
                StringEscapeUtils.escapeHtml(
                     loginHistoryResolution.getUrl(getContext().getLocale()));
            getContext().getMessages().add(new ScopedLocalizableMessage(LoginActionBean.class,
                                                                        "loginSucceededWithLastLogin",
                                                                        dateFormat.format(historicLogin.getDate()),
                                                                        timeFormat.format(historicLogin.getDate()),
                                                                        historicLogin.getIp(),
                                                                        loginHistoryUrl));
        }

        if (requestedUrl != null) {
            return new RedirectResolution(requestedUrl, false);
        }

        return new RedirectResolution(CardsActionBean.class);
    }

    /**
     * Logs a user out
     * @return a redirect resolution to the welcome page
     */
    @DontValidate
    public Resolution logout() {
        getContext().logout();
        return new RedirectResolution(IndexActionBean.class);
    }

    /**
     * Gets the user ID
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID
     * @param userId the new userID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the master password
     * @return the master password
     */
    public String getMasterPassword() {
        return masterPassword;
    }

    /**
     * Sets the master password
     * @param masterPassword the new master password
     */
    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    /**
     * Gets the requested URL
     * @return the requested URL
     */
    public String getRequestedUrl() {
        return requestedUrl;
    }

    /**
     * Sets the requested URL
     * @param requestedUrl the new requested URL
     */
    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }
}
