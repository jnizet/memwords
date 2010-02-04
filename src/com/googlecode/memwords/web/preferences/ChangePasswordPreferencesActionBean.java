package com.googlecode.memwords.web.preferences;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.Preferences;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to change preferences regarding passwords (masked by default or not)
 * @author JB
 */
public class ChangePasswordPreferencesActionBean extends MwActionBean {

    /**
     * Input field containing the masking preference
     */
    private boolean unmasked;

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * Constructor
     * @param accountService the account service
     */
    @Inject
    public ChangePasswordPreferencesActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the change password preferences page
     * @return a forward resolution to the change password preferences page
     */
    @DefaultHandler
    public Resolution view() {
        loadUnmasked();
        return new ForwardResolution("/preferences/changePasswordPreferences.jsp");
    }

    /**
     * Displays the change password preferences section using AJAX
     * @return a forward resolution which updates the preferences page with the
     * change password preferences form
     */
    public Resolution ajaxView() {
        loadUnmasked();
        return new ForwardResolution("/preferences/ajaxChangePasswordPreferences.jsp");
    }

    /**
     * Loads the unmasked preference
     */
    private void loadUnmasked() {
        this.unmasked = getContext().getUserInformation().getPreferences().isPasswordsUnmasked();
    }

    /**
     * Changes the password preferences
     * @return a redirect resolution to the preferences page, with a success message
     */
    public Resolution change() {
        doChange();
        return new RedirectResolution(PreferencesActionBean.class);
    }

    /**
     * Changes the password preferences using AJAX
     * @return a forward resolution which updates the preferences page with a success message
     * and a clean preferences page
     */
    public Resolution ajaxChange() {
        doChange();
        return new ForwardResolution("/preferences/ajaxPreferences.jsp");
    }

    /**
     * Performs the change
     */
    private void doChange() {
        UserInformation userInformation = getContext().getUserInformation();
        Preferences preferences = userInformation.getPreferences();
        Preferences newPreferences = preferences.withPasswordsUnmasked(this.unmasked);
        accountService.changePreferences(userInformation.getUserId(),
                                         newPreferences);
        getContext().setUserInformation(
            userInformation.withPreferences(newPreferences));
        getContext().getMessages().add(
            new ScopedLocalizableMessage(ChangePasswordPreferencesActionBean.class,
                                         "passwordPreferencesChanged"));
    }

    /**
     * Cancels the change
     * @return a redirect resolution to the preferences page
     */
    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(PreferencesActionBean.class);
    }

    /**
     * Gets the unmasked preference
     * @return the unmasked preference
     */
    public boolean isUnmasked() {
        return unmasked;
    }

    /**
     * Sets the unmasked preference
     * @param unmasked the new unmasked preference
     */
    public void setUnmasked(boolean unmasked) {
        this.unmasked = unmasked;
    }
}
