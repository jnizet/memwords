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
 * Action used to change preferences regarding passwords (masked by default or not)
 * @author JB
 */
public class ChangePasswordPreferencesActionBean extends MwActionBean {
    private boolean unmasked;

    private AccountService accountService;

    @Inject
    public ChangePasswordPreferencesActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    @DefaultHandler
    public Resolution view() {
        loadUnmasked();
        return new ForwardResolution("/preferences/changePasswordPreferences.jsp");
    }

    public Resolution ajaxView() {
        loadUnmasked();
        return new ForwardResolution("/preferences/ajaxChangePasswordPreferences.jsp");
    }

    private void loadUnmasked() {
        this.unmasked = getContext().getUserInformation().getPreferences().isPasswordsUnmasked();
    }

    public Resolution change() {
        doChange();
        return new RedirectResolution(PreferencesActionBean.class);
    }

    public Resolution ajaxChange() {
        doChange();
        return new ForwardResolution("/preferences/ajaxPreferences.jsp");
    }

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

    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(PreferencesActionBean.class);
    }

    public boolean isUnmasked() {
        return unmasked;
    }

    public void setUnmasked(boolean unmasked) {
        this.unmasked = unmasked;
    }
}
