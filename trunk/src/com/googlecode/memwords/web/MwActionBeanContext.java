package com.googlecode.memwords.web;

import com.googlecode.memwords.domain.UserInformation;

import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * The subclass of ActionBeranContext used in this application
 * @author JB
 */
public class MwActionBeanContext extends ActionBeanContext {
	
	public static final String USER_INFORMATION_SESSION_ATTRIBUTE = "userInformation";
	
	public void setUserInformation(UserInformation info) {
		getRequest().getSession().setAttribute(USER_INFORMATION_SESSION_ATTRIBUTE, info);
	}
	
	public UserInformation getUserInformation() {
		return (UserInformation) getRequest().getSession().getAttribute(USER_INFORMATION_SESSION_ATTRIBUTE);
	}
	
	public boolean isLoggedIn() {
		return getUserInformation() != null;
	}
	
	public String getSessionId() {
		return getRequest().getSession().getId();
	}
}
