package com.googlecode.memwords.web.account;

import java.io.IOException;

import javax.crypto.SecretKey;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.IndexActionBean;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.cards.CardsActionBean;

/**
 * Action bean used to handle the login
 * @author JB
 */
public class LoginActionBean extends MwActionBean {

    private AccountService accountService;

    @Validate(required = true)
    private String userId;

    @Validate(required = true)
    private String masterPassword;

    private String requestedUrl;

    @Inject
    public LoginActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        this.requestedUrl = getContext().getRequestedUrl();
        return new ForwardResolution("/account/login.jsp");
    }

    public Resolution login() throws IOException {
        SecretKey encryptionKey =
            accountService.login(userId, masterPassword);
        if (encryptionKey == null) {
            getContext().getValidationErrors().addGlobalError(
                    new LocalizableError("loginFailed"));
            return getContext().getSourcePageResolution();
        }
        getContext().login(new UserInformation(userId, encryptionKey));

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
