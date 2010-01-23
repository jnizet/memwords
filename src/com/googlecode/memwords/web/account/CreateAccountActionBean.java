package com.googlecode.memwords.web.account;

import javax.crypto.SecretKey;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.cards.CardsActionBean;

/**
 * Action bean used to handle the creation of an account
 * @author JB
 */
public class CreateAccountActionBean extends MwActionBean {

    private static final int MASTER_PASSWORD_MIN_LENGTH = 4;

    private AccountService accountService;

    @Validate(required = true)
    private String userId;

    @Validate(required = true, minlength = MASTER_PASSWORD_MIN_LENGTH)
    private String masterPassword;

    @Validate(required = true, expression = "this == masterPassword")
    private String masterPassword2;

    private boolean userIdAvailable;

    @Inject
    public CreateAccountActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution("/account/createAccount.jsp");
    }

    public Resolution createAccount() {

        SecretKey encryptionKey =
            accountService.createAccount(userId, masterPassword);
        getContext().setUserInformation(new UserInformation(userId, encryptionKey));
        return new RedirectResolution(CardsActionBean.class);
    }

    @ValidationMethod(on = "createAccount", when = ValidationState.ALWAYS)
    public void validateUserIdExists(ValidationErrors errors) {
        if (!errors.containsKey("userId")
            && accountService.accountExists(userId)) {
            errors.add("userId", new LocalizableError("userIdNotAvailable"));
        }
    }

    @DontValidate
    public Resolution ajaxGetUserIdAvailability() {
        this.userIdAvailable = !accountService.accountExists(this.userId);
        return new ForwardResolution("/account/_userIdAvailability.jsp");
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

    public String getMasterPassword2() {
        return masterPassword2;
    }

    public void setMasterPassword2(String masterPassword2) {
        this.masterPassword2 = masterPassword2;
    }

    public boolean isUserIdAvailable() {
        return userIdAvailable;
    }
}
