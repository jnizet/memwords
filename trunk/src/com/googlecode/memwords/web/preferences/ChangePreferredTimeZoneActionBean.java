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
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to change the preferred time zone
 * @author JB
 */
public class ChangePreferredTimeZoneActionBean extends MwActionBean implements ValidationErrorHandler {

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
        this.timeZoneId = getContext().getTimeZone().getID();
        return new ForwardResolution("/preferences/changePreferredTimeZone.jsp");
    }

    public Resolution change() {
        UserInformation userInformation = getContext().getUserInformation();
        TimeZone timeZone = TimeZone.getTimeZone(this.timeZoneId);
        accountService.changePreferredTimeZone(userInformation.getUserId(),
                                               timeZone);
        getContext().setUserInformation(userInformation.withPreferredTimeZone(timeZone));
        getContext().getMessages().add(new ScopedLocalizableMessage(ChangePreferredTimeZoneActionBean.class,
                                                                    "preferredTimeZoneChanged"));
        return new RedirectResolution(PreferencesActionBean.class);
    }

    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(PreferencesActionBean.class);
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) {
        loadTimeZones();
        return null;
    }

    private void loadTimeZones() {
        String[] ids = TimeZone.getAvailableIDs();
        this.timeZones = new ArrayList<TimeZone>(ids.length);
        for (String id : ids) {
            timeZones.add(TimeZone.getTimeZone(id));
        }
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
