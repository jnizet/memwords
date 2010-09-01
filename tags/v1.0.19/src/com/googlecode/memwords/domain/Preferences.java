package com.googlecode.memwords.domain;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import net.jcip.annotations.Immutable;

/**
 * Preferences of an account (immutable value object)
 * @author JB
 */
@Immutable
public class Preferences implements Serializable {

    /**
     * The preferred locale (nullable)
     */
    private final Locale locale;

    /**
     * The preferred time zone (not nullable)
     */
    private final TimeZone timeZone;

    /**
     * Flag indicating if passwords must be unmasked by default
     */
    private final boolean passwordsUnmasked;

    /**
     * The password generation preferences (not nullable)
     */
    private final PasswordGenerationPreferences passwordGenerationPreferences;

    /**
     * Constructor
     * @param locale the preferred locale (nullable)
     * @param timeZone the preferred time zone (not nullable)
     * @param passwordsUnmasked flag indicating if passwords must be unmasked by default
     * @param passwordGenerationPreferences the password generation preferences
     */
    public Preferences(Locale locale,
                       TimeZone timeZone,
                       boolean passwordsUnmasked,
                       PasswordGenerationPreferences passwordGenerationPreferences) {
        if (timeZone == null) {
            throw new IllegalArgumentException("The time zone may not be null");
        }
        if (passwordGenerationPreferences == null) {
            throw new IllegalArgumentException("The password generation preferences may not be null");
        }
        this.locale = locale;
        this.timeZone = timeZone;
        this.passwordsUnmasked = passwordsUnmasked;
        this.passwordGenerationPreferences = passwordGenerationPreferences;
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
     * Gets the password generation preferences
     * @return the password generation preferences
     */
    public PasswordGenerationPreferences getPasswordGenerationPreferences() {
        return passwordGenerationPreferences;
    }

    /**
     * Creates a copy of this object with a different locale
     * @param locale the different locale (nullable)
     * @return the copy of this instance, with the given locale
     */
    public Preferences withLocale(Locale locale) {
        return new Preferences(locale,
                               this.timeZone,
                               this.passwordsUnmasked,
                               this.passwordGenerationPreferences);
    }

    /**
     * Creates a copy of this object with a different time zone
     * @param timeZone the different time zone (not nullable)
     * @return the copy of this instance, with the given time zone
     */
    public Preferences withTimeZone(TimeZone timeZone) {
        return new Preferences(this.locale,
                               timeZone,
                               this.passwordsUnmasked,
                               this.passwordGenerationPreferences);
    }

    /**
     * Creates a copy of this object with a different preferences regarding password masking
     * @param passwordsUnmasked the different flag
     * @return the copy of this instance, with the given preference
     */
    public Preferences withPasswordsUnmasked(boolean passwordsUnmasked) {
        return new Preferences(this.locale,
                               this.timeZone,
                               passwordsUnmasked,
                               this.passwordGenerationPreferences);
    }

    /**
     * Creates a copy of this object with different password generation preferences
     * @param preferences the different preferences (not nullable)
     * @return the copy of this instance, with the given preferences
     */
    public Preferences withPasswordGenerationPreferences(PasswordGenerationPreferences preferences) {
        return new Preferences(this.locale,
                               this.timeZone,
                               this.passwordsUnmasked,
                               preferences);
    }
}
