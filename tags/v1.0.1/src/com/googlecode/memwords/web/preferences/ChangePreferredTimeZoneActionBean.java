package com.googlecode.memwords.web.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.Preferences;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to change the preferred time zone
 * @author JB
 */
public class ChangePreferredTimeZoneActionBean extends MwActionBean {

    @Validate(required = true)
    private String timeZoneId;

    private List<TimeZone> timeZones;

    private AccountService accountService;

    @Inject
    public ChangePreferredTimeZoneActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        loadTimeZones();
        loadTimeZone();
        return new ForwardResolution("/preferences/changePreferredTimeZone.jsp");
    }

    @DontValidate
    public Resolution ajaxView() {
        loadTimeZones();
        loadTimeZone();
        return new ForwardResolution("/preferences/ajaxChangePreferredTimeZone.jsp");
    }

    private void loadTimeZones() {
        String[] ids = TimeZone.getAvailableIDs();
        this.timeZones = new ArrayList<TimeZone>(ids.length);
        for (String id : ids) {
            timeZones.add(TimeZone.getTimeZone(id));
        }
    }

    private void loadTimeZone() {
        this.timeZoneId = getContext().getTimeZone().getID();
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
        TimeZone timeZone = TimeZone.getTimeZone(this.timeZoneId);
        Preferences newPreferences = preferences.withTimeZone(timeZone);
        accountService.changePreferences(userInformation.getUserId(), newPreferences);
        getContext().setUserInformation(
            userInformation.withPreferences(newPreferences));
        getContext().getMessages().add(new ScopedLocalizableMessage(ChangePreferredTimeZoneActionBean.class,
                                                                    "preferredTimeZoneChanged"));
    }

    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(PreferencesActionBean.class);
    }

    public List<TimeZone> getTimeZones() {
        return timeZones;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
