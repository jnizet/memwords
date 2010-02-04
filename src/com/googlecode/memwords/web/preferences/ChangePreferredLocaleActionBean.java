package com.googlecode.memwords.web.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.googlecode.memwords.web.MwLocalePicker;
import com.googlecode.memwords.web.util.LocaleTypeConverter;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to change the preferred locale
 * @author JB
 */
public class ChangePreferredLocaleActionBean extends MwActionBean {

    /**
     * Input field containing the selected locale (<code>null</code> if no locale selected)
     */
    @Validate(converter = LocaleTypeConverter.class)
    private Locale locale;

    /**
     * The list of selectable locales
     */
    private List<DisplayedLocale> supportedLocales;

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * Constructor
     * @param accountService the account service
     */
    @Inject
    public ChangePreferredLocaleActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the change preferred locale page
     * @return a forward resolution to the change preferred locale page
     */
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        loadSupportedLocales();
        loadLocale();
        return new ForwardResolution("/preferences/changePreferredLocale.jsp");
    }

    /**
     * Displays the change preferred locale section using AJAX
     * @return a forward resolution which updates the preferences page with the
     * change preferred locale form
     */
    @DontValidate
    public Resolution ajaxView() {
        loadSupportedLocales();
        loadLocale();
        return new ForwardResolution("/preferences/ajaxChangePreferredLocale.jsp");
    }

    /**
     * loads the supported locales
     */
    private void loadSupportedLocales() {
        supportedLocales = new ArrayList<DisplayedLocale>(MwLocalePicker.SUPPORTED_LOCALES.size());
        for (Locale l : MwLocalePicker.SUPPORTED_LOCALES) {
            supportedLocales.add(new DisplayedLocale(l));
        }
    }

    /**
     * loads the preferred locale
     */
    private void loadLocale() {
        this.locale = getContext().getUserInformation().getPreferences().getLocale();
    }

    /**
     * Changes the preferred locale
     * @return a redirect resolution to the preferences page, with a success message
     */
    public Resolution change() {
        UserInformation userInformation = getContext().getUserInformation();
        Preferences preferences = userInformation.getPreferences();
        Preferences newPreferences = preferences.withLocale(this.locale);
        accountService.changePreferences(userInformation.getUserId(), newPreferences);
        getContext().setUserInformation(
            userInformation.withPreferences(newPreferences));
        getContext().getMessages().add(new ScopedLocalizableMessage(ChangePreferredLocaleActionBean.class,
                                                                    "preferredLocaleChanged"));
        return new RedirectResolution(PreferencesActionBean.class);
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
     * Gets the list of supported locales
     * @return the list of supported locales
     */
    public List<DisplayedLocale> getSupportedLocales() {
        return supportedLocales;
    }

    /**
     * Gets the locale
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the locale
     * @param locale the new locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
