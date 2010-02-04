package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Preferences of an account (immutable value object)
 * @author JB
 */
public class Preferences implements Serializable {

    /**
     * The preferred locale (nullable)
     */
    private Locale locale;

    /**
     * The preferred time zone (nullable)
     */
    private TimeZone timeZone;

    /**
     * Flag indicating if passwords must be unmasked by default
     */
    private boolean passwordsUnmasked;

    /**
     * Constructor
     * @param locale the preferred locale (nullable)
     * @param timeZone the preferred time zone (nullable)
     * @param passwordsUnmasked flag indicating if passwords must be unmasked by default
     */
    public Preferences(Locale locale, TimeZone timeZone, boolean passwordsUnmasked) {
        this.locale = locale;
        this.timeZone = timeZone;
        this.passwordsUnmasked = passwordsUnmasked;
    }

    /**
     * Gets the preferred locale
     * @return the preferred locale, or <code>null</code> if there is none
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Gets the preferred time zone
     * @return the preferred time zone, or <code>null</code> if there is none
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Gets the preference about password masking
     * @return <code>true</code> if passwords must be unmasked by default, <code>false</code> otherwise
     */
    public boolean isPasswordsUnmasked() {
        return passwordsUnmasked;
    }

    /**
     * Creates a copy of this object with a different locale
     * @param locale the different locale (nullable)
     * @return the copy of this instance, with the given locale
     */
    public Preferences withLocale(Locale locale) {
        return new Preferences(locale, this.timeZone, this.passwordsUnmasked);
    }

    /**
     * Creates a copy of this object with a different time zone
     * @param timeZone the different time zone (nullable)
     * @return the copy of this instance, with the given time zone
     */
    public Preferences withTimeZone(TimeZone timeZone) {
        return new Preferences(this.locale, timeZone, this.passwordsUnmasked);
    }

    /**
     * Creates a copy of this object with a different preferences regarding password masking
     * @param passwordsUnmasked the different flag
     * @return the copy of this instance, with the given preference
     */
    public Preferences withPasswordsUnmasked(boolean passwordsUnmasked) {
        return new Preferences(this.locale, this.timeZone, passwordsUnmasked);
    }
}
