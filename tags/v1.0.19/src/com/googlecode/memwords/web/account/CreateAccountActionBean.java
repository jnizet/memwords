package com.googlecode.memwords.web.account;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.MwConstants;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.cards.CardsActionBean;

/**
 * Action bean used to handle the creation of an account
 * @author JB
 */
@HttpCache(allow = false)
public class CreateAccountActionBean extends MwActionBean {

    /**
     * Input field containing the user ID of the account to create
     */
    @Validate(required = true)
    private String userId;

    /**
     * Input field containing the master password of the account to create
     */
    @Validate(required = true, minlength = MwConstants.MASTER_PASSWORD_MIN_LENGTH)
    private String masterPassword;

    /**
     * Input field containing the master password confirmation
     */
    @Validate(required = true, expression = "this == masterPassword")
    private String masterPassword2;

    /**
     * Output field indicating if the entered user ID is available
     */
    private boolean userIdAvailable;

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * Constructor
     * @param accountService the account service
     */
    @Inject
    public CreateAccountActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the page allowing to create a new account
     * @return a forward resolution to the create account page
     */
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution("/account/createAccount.jsp");
    }

    /**
     * Creates the account
     * @return a redirect resolution to the cards page
     */
    public Resolution createAccount() {
        UserInformation userInformation =
            accountService.createAccount(userId, masterPassword);
        getContext().login(userInformation);
        return new RedirectResolution(CardsActionBean.class);
    }

    /**
     * Custom validation method used to check that the user ID doesn't exist
     * @param errors the errors to update
     */
    @ValidationMethod(on = "createAccount", when = ValidationState.ALWAYS)
    public void validateUserIdDoesntExist(ValidationErrors errors) {
        if (!errors.containsKey("userId")
            && accountService.accountExists(userId)) {
            errors.add("userId", new LocalizableError("userIdNotAvailable"));
        }
    }

    /**
     * Ajax event handler used to get a page fragment indicating if the user ID is available or not
     * @return a forward resolution to the fragment page
     */
    @DontValidate
    public Resolution ajaxGetUserIdAvailability() {
        this.userIdAvailable = !accountService.accountExists(this.userId);
        return new ForwardResolution("/account/_userIdAvailability.jsp");
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
     * @param userId the new user ID
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
     * Gets the master password confirmation
     * @return the master password confirmation
     */
    public String getMasterPassword2() {
        return masterPassword2;
    }

    /**
     * Sets the master password confirmation
     * @param masterPassword2 the new master password confirmation
     */
    public void setMasterPassword2(String masterPassword2) {
        this.masterPassword2 = masterPassword2;
    }

    /**
     * Indicates if the user ID is available, after the {@link #ajaxGetUserIdAvailability()}
     * event has been run
     * @return <code>true</code> if the user ID is available, <code>false</code> otherwise
     */
    public boolean isUserIdAvailable() {
        return userIdAvailable;
    }
}
