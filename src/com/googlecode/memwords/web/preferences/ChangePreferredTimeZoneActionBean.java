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

    /**
     * Input field containing the ID of the preferred time zone
     */
    @Validate(required = true)
    private String timeZoneId;

    /**
     * The list of selectable time zones
     */
    private List<TimeZone> timeZones;

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * Constructor
     * @param accountService the account service
     */
    @Inject
    public ChangePreferredTimeZoneActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the change preferred time zone page
     * @return a forward resolution to the change preferred time zone page
     */
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        loadTimeZones();
        loadTimeZone();
        return new ForwardResolution("/preferences/changePreferredTimeZone.jsp");
    }

    /**
     * Displays the change preferred time zone section using AJAX
     * @return a forward resolution which updates the preferences page with the
     * change preferred time zone form
     */
    @DontValidate
    public Resolution ajaxView() {
        loadTimeZones();
        loadTimeZone();
        return new ForwardResolution("/preferences/ajaxChangePreferredTimeZone.jsp");
    }

    /**
     * Loads the selectable tie zones
     */
    private void loadTimeZones() {
        String[] ids = TimeZone.getAvailableIDs();
        this.timeZones = new ArrayList<TimeZone>(ids.length);
        for (String id : ids) {
            timeZones.add(TimeZone.getTimeZone(id));
        }
    }

    /**
     * Loads the preferred (or default) time zone
     */
    private void loadTimeZone() {
        this.timeZoneId = getContext().getTimeZone().getID();
    }

    /**
     * Changes the preferred time zone
     * @return a redirect resolution to the preferences page, with a success message
     */
    public Resolution change() {
        doChange();
        return new RedirectResolution(PreferencesActionBean.class);
    }

    /**
     * Changes the preferred time zone using AJAX
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
        TimeZone timeZone = TimeZone.getTimeZone(this.timeZoneId);
        Preferences newPreferences = preferences.withTimeZone(timeZone);
        accountService.changePreferences(userInformation.getUserId(), newPreferences);
        getContext().setUserInformation(
            userInformation.withPreferences(newPreferences));
        getContext().getMessages().add(new ScopedLocalizableMessage(ChangePreferredTimeZoneActionBean.class,
                                                                    "preferredTimeZoneChanged"));
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
     * Gets the list of selectable time zones
     * @return the list of selectable time zones
     */
    public List<TimeZone> getTimeZones() {
        return timeZones;
    }

    /**
     * Gets the time zone ID
     * @return the time zone ID
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * Sets the time zone ID
     * @param timeZoneId the new time zone ID
     */
    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
