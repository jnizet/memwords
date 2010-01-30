package com.googlecode.memwords.web.account;

import java.io.IOException;
import java.text.DateFormat;
import java.util.TimeZone;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.HistoricLogin;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.facade.loginhistory.LoginHistoryService;
import com.googlecode.memwords.web.IndexActionBean;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.cards.CardsActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to handle the login
 * @author JB
 */
public class LoginActionBean extends MwActionBean {

    private AccountService accountService;
    private LoginHistoryService loginHistoryService;

    @Validate(required = true)
    private String userId;

    @Validate(required = true)
    private String masterPassword;

    private String requestedUrl;

    @Inject
    public LoginActionBean(AccountService accountService,
                           LoginHistoryService loginHistoryService) {
        this.accountService = accountService;
        this.loginHistoryService = loginHistoryService;
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        this.requestedUrl = getContext().getRequestedUrl();
        return new ForwardResolution("/account/login.jsp");
    }

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
            getContext().getMessages().add(new ScopedLocalizableMessage(LoginActionBean.class,
                                                                        "loginSucceededWithLastLogin",
                                                                        dateFormat.format(historicLogin.getDate()),
                                                                        timeFormat.format(historicLogin.getDate()),
                                                                        historicLogin.getIp()));
        }

        if (requestedUrl != null) {
            return new RedirectResolution(requestedUrl, false);
        }

        return new RedirectResolution(CardsActionBean.class);
    }

    @DontValidate
    public Resolution logout() {
        getContext().logout();
        return new RedirectResolution(IndexActionBean.class);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getRequestedUrl() {
        return requestedUrl;
    }

    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }
}
