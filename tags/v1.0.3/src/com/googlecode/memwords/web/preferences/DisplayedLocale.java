package com.googlecode.memwords.web.preferences;

import java.io.Serializable;
import java.util.Locale;

/**
 * Object containing a locale and its name displayed in its own language
 * @author JB
 */
public class DisplayedLocale implements Serializable {
    private Locale locale;
    private String displayedName;

    public DisplayedLocale(Locale locale) {
        this.locale = locale;
        this.displayedName = locale.getDisplayName(this.locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public String getDisplayedName() {
        return displayedName;
    }
}
