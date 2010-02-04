package com.googlecode.memwords.web.preferences;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import com.googlecode.memwords.web.MwActionBean;

/**
 * Action bean used to display the main preferences page
 * @author JB
 */
public class PreferencesActionBean extends MwActionBean {

    /**
     * Displays the preferences page
     * @return a forward resolution to the preferences page
     */
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution("/preferences/preferences.jsp");
    }
}
