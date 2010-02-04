package com.googlecode.memwords.web.preferences;

import java.io.Serializable;
import java.util.Locale;

/**
 * Object containing a locale and its name displayed in its own language
 * @author JB
 */
public final class DisplayedLocale implements Serializable {

    /**
     * The locale
     */
    private Locale locale;

    /**
     * The name of the locale, in its own language
     */
    private String displayedName;

    /**
     * Constructor
     * @param locale the locale
     */
    public DisplayedLocale(Locale locale) {
        this.locale = locale;
        this.displayedName = locale.getDisplayName(this.locale);
    }

    /**
     * Gets the locale
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Gets the name of the locale, in its own language
     * @return the name of the locale, in its own language
     */
    public String getDisplayedName() {
        return displayedName;
    }
}
