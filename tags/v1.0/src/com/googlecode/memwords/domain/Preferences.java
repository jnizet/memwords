package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Preferences of an account
 * @author JB
 */
public class Preferences implements Serializable {
    private Locale locale;
    private TimeZone timeZone;
    private boolean passwordsUnmasked;

    public Preferences(Locale locale, TimeZone timeZone, boolean passwordsUnmasked) {
        super();
        this.locale = locale;
        this.timeZone = timeZone;
        this.passwordsUnmasked = passwordsUnmasked;
    }

    public Locale getLocale() {
        return locale;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public boolean isPasswordsUnmasked() {
        return passwordsUnmasked;
    }

    public Preferences withLocale(Locale locale) {
        return new Preferences(locale, this.timeZone, this.passwordsUnmasked);
    }

    public Preferences withTimeZone(TimeZone timeZone) {
        return new Preferences(this.locale, timeZone, this.passwordsUnmasked);
    }

    public Preferences withPasswordsUnmasked(boolean passwordsUnmasked) {
        return new Preferences(this.locale, this.timeZone, passwordsUnmasked);
    }
}
