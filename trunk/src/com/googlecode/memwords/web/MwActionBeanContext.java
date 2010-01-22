package com.googlecode.memwords.web;

import net.sourceforge.stripes.action.ActionBeanContext;

import com.googlecode.memwords.domain.UserInformation;

/**
 * The subclass of ActionBeranContext used in this application
 * @author JB
 */
public class MwActionBeanContext extends ActionBeanContext {

    public static final String USER_INFORMATION_SESSION_ATTRIBUTE = "userInformation";
    public static final String REQUESTED_URL_REQUEST_ATTRIBUTE = "requestedUrl";

    public void setUserInformation(UserInformation info) {
        getRequest().getSession().setAttribute(USER_INFORMATION_SESSION_ATTRIBUTE, info);
    }

    public UserInformation getUserInformation() {
        return (UserInformation) getRequest().getSession().getAttribute(USER_INFORMATION_SESSION_ATTRIBUTE);
    }

    public void setRequestedUrl(String url) {
        getRequest().setAttribute(REQUESTED_URL_REQUEST_ATTRIBUTE, url);
    }

    public String getRequestedUrl() {
        return (String) getRequest().getAttribute(REQUESTED_URL_REQUEST_ATTRIBUTE);
    }

    public boolean isLoggedIn() {
        return getUserInformation() != null;
    }
}
