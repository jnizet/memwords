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

    @Validate(converter = LocaleTypeConverter.class)
    private Locale locale;

    private List<DisplayedLocale> supportedLocales;

    private AccountService accountService;

    @Inject
    public ChangePreferredLocaleActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        loadSupportedLocales();
        loadLocale();
        return new ForwardResolution("/preferences/changePreferredLocale.jsp");
    }

    @DontValidate
    public Resolution ajaxView() {
        loadSupportedLocales();
        loadLocale();
        return new ForwardResolution("/preferences/ajaxChangePreferredLocale.jsp");
    }

    private void loadSupportedLocales() {
        supportedLocales = new ArrayList<DisplayedLocale>(MwLocalePicker.SUPPORTED_LOCALES.size());
        for (Locale l : MwLocalePicker.SUPPORTED_LOCALES) {
            supportedLocales.add(new DisplayedLocale(l));
        }
    }

    private void loadLocale() {
        this.locale = getContext().getUserInformation().getPreferences().getLocale();
    }

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

    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(PreferencesActionBean.class);
    }

    public List<DisplayedLocale> getSupportedLocales() {
        return supportedLocales;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
