package com.googlecode.memwords.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.DefaultLocalePicker;

/**
 * The locale picker usd by MemWords. Rather than using a list of locales
 * and encodings found in the web.xml file, it used a hard-coded list.
 * The locale picking algorithm is the same as the default one, except it first
 * looks up into the session, in order for the user to explicitely choose another
 * locale via the application, among the hard-coded list of supported locales.
 * @author JB
 */
public class MwLocalePicker extends DefaultLocalePicker {

    /**
     * The hard-coded, read-only list of supported locales
     */
    public static final List<Locale> SUPPORTED_LOCALES;

    /**
     * The map of encodings by locale
     */
    private static final Map<Locale, String> ENCODINGS;

    /**
     * The session attribute where the preferred locale is stored
     */
    private static final String PREFERRED_LOCALE_SESSION_ATTRIBUTE =
        "com.googlecode.memwords.web.preferredLocale";

    static {
        List<Locale> locales = new ArrayList<Locale>();
        locales.add(Locale.ENGLISH);
        locales.add(Locale.FRENCH);
        locales.add(Locale.ITALIAN);

        SUPPORTED_LOCALES = Collections.unmodifiableList(locales);

        ENCODINGS = Collections.emptyMap();
    }

    @Override
    public void init(Configuration configuration) {
        this.configuration = configuration;
        this.locales = SUPPORTED_LOCALES;
        this.encodings = ENCODINGS;
    }

    @Override
    public Locale pickLocale(HttpServletRequest request) {
        Locale result = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            Locale preferredLocale = (Locale) session.getAttribute(PREFERRED_LOCALE_SESSION_ATTRIBUTE);
            if (preferredLocale != null) {
                result = preferredLocale;
            }
        }
        if (result == null) {
            result = super.pickLocale(request);
        }
        return result;
    }

    /**
     * Sets the preferred locale in the session, so that it's picked up by this locale picker
     * for subsequent requests
     * @param request the current request
     * @param preferredLocale the preferred locale
     */
    public static void setPreferredLocale(HttpServletRequest request, Locale preferredLocale) {
        HttpSession session = request.getSession(true);
        session.setAttribute(PREFERRED_LOCALE_SESSION_ATTRIBUTE, preferredLocale);
    }
}
