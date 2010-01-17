package com.googlecode.memwords.web;

import com.googlecode.memwords.domain.UserInformation;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.StripesConstants;

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
	
	public String getSessionId() {
		return getRequest().getSession().getId();
	}
	
	public boolean isHasMessages() {
		return getRequest().getAttribute(StripesConstants.REQ_ATTR_MESSAGES) != null;
	}
}
