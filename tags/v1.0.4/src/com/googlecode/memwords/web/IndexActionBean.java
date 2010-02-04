package com.googlecode.memwords.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * Index action, which displays the welcome page.
 * @author JB
 */
public class IndexActionBean extends MwActionBean {

    /**
     * Displays the welcome page
     * @return a forward resolution to the welcome page
     */
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution("/welcome.jsp");
    }
}
