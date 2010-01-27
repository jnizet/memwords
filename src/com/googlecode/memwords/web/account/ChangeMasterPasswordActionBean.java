package com.googlecode.memwords.web.account;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
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
import com.googlecode.memwords.domain.MwConstants;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to change the master password
 * @author JB
 */
public class ChangeMasterPasswordActionBean extends MwActionBean {

    @Validate(required = true)
    private String currentPassword;

    @Validate(required = true, minlength = MwConstants.MASTER_PASSWORD_MIN_LENGTH)
    private String newPassword;

    @Validate(required = true,
              expression = "this == newPassword")
    private String newPasswordConfirmation;

    private AccountService accountService;

    @Inject
    public ChangeMasterPasswordActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution("/account/changeMasterPassword.jsp");
    }

    public Resolution change() {
        accountService.changePassword(getContext().getUserInformation().getUserId(),
                                      newPassword,
                                      getContext().getUserInformation().getEncryptionKey());
        getContext().getMessages().add(new ScopedLocalizableMessage(
            ChangeMasterPasswordActionBean.class,
            "masterPasswordChanged"));
        return new RedirectResolution(AccountActionBean.class);
    }

    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(AccountActionBean.class);
    }

    @ValidationMethod(on = "change", when = ValidationState.ALWAYS)
    public Resolution validateCurrentPassword(ValidationErrors errors) {
        if (!errors.containsKey("currentPassword")
            && !accountService.checkPassword(getContext().getUserInformation().getUserId(),
                                             currentPassword)) {
            errors.add("currentPassword", new LocalizableError("badCurrentPassword"));
        }
        return null;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}