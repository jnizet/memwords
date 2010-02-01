package com.googlecode.memwords.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 * Action bean used to display screenshots
 * @author JB
 */
public class ScreenshotsActionBean extends MwActionBean {
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution("/screenshots.jsp");
    }
}
