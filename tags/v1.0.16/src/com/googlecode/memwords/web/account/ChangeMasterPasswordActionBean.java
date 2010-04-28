package com.googlecode.memwords.web.account;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
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
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to change the master password of the user's account
 * @author JB
 */
@HttpCache(allow = false)
public class ChangeMasterPasswordActionBean extends MwActionBean {

    /**
     * Input field, containing the current password
     */
    @Validate(required = true)
    private String currentPassword;

    /**
     * Input field, containing the new password
     */
    @Validate(required = true, minlength = MwConstants.MASTER_PASSWORD_MIN_LENGTH)
    private String newPassword;

    /**
     * Input field, containing the new password confirmation
     */
    @Validate(required = true,
              expression = "this == newPassword")
    private String newPasswordConfirmation;

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * Constructor
     * @param accountService the account service
     */
    @Inject
    public ChangeMasterPasswordActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the page used to change the master password
     * @return a forward resolution to the change master password page
     */
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution("/account/changeMasterPassword.jsp");
    }

    /**
     * Changes the master password
     * @return a redirect resolution to the account page, with a success message
     */
    public Resolution change() {
        accountService.changePassword(getContext().getUserInformation().getUserId(),
                                      newPassword,
                                      getContext().getUserInformation().getEncryptionKey());
        getContext().getMessages().add(new ScopedLocalizableMessage(
            ChangeMasterPasswordActionBean.class,
            "masterPasswordChanged"));
        return new RedirectResolution(AccountActionBean.class);
    }

    /**
     * Cancels the change
     * @return a redirect resolution to the account page
     */
    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(AccountActionBean.class);
    }

    /**
     * Validation method to check that the current password is the right one
     * @param errors the errors to update
     * @return <code>null</code>
     */
    @ValidationMethod(on = "change", when = ValidationState.ALWAYS)
    public Resolution validateCurrentPassword(ValidationErrors errors) {
        if (!errors.containsKey("currentPassword")
            && !accountService.checkPassword(getContext().getUserInformation().getUserId(),
                                             currentPassword)) {
            errors.add("currentPassword", new LocalizableError("badCurrentPassword"));
        }
        return null;
    }

    /**
     * Gets the current password
     * @return the current password
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Sets the current password
     * @param currentPassword the new current password
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Gets the new password
     * @return the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password
     * @param newPassword the new new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Gets the new password confirmation
     * @return the new password confirmation
     */
    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    /**
     * Sets the new password confirmation
     * @param newPasswordConfirmation the new new password confirmation
     */
    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}