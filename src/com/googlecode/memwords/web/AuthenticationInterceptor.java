package com.googlecode.memwords.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.memwords.web.account.CreateAccountActionBean;
import com.googlecode.memwords.web.account.LoginActionBean;
import com.googlecode.memwords.web.util.AjaxUtils;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

@Intercepts(LifecycleStage.HandlerResolution)
public class AuthenticationInterceptor implements Interceptor {
	
	public Resolution intercept(ExecutionContext ctx) throws Exception {
    	if (isPermittedWithoutLogin(ctx.getActionBean(), ctx.getActionBeanContext())) {
        	return ctx.proceed();
        }
        else if (!isLoggedIn(ctx.getActionBeanContext())) {
        	if (AjaxUtils.isAjaxRequest(ctx.getActionBeanContext().getRequest())) {
        		return new ErrorResolution(HttpServletResponse.SC_FORBIDDEN, null);
        	}
        	addRequestedUrlToContext(ctx.getActionBeanContext());
            return new ForwardResolution(LoginActionBean.class);
        }
        else {
        	return ctx.proceed();
        }
    }

    private void addRequestedUrlToContext(ActionBeanContext actionBeanContext) {
    	MwActionBeanContext ctx = (MwActionBeanContext) actionBeanContext;
    	HttpServletRequest request = ctx.getRequest();
    	if ("GET".equals(request.getMethod())) {
    		String url = request.getRequestURL().toString();
    		if (request.getQueryString() != null) {
    			url = url + "?" + request.getQueryString();
    		}
    		ctx.setRequestedUrl(url);
    	}
	}

	private boolean isPermittedWithoutLogin(ActionBean actionBean,
			                                ActionBeanContext actionBeanContext) {
		Class<?> actionBeanClass = actionBean.getClass();
    	return (actionBeanClass.equals(IndexActionBean.class)
    			|| actionBeanClass.equals(LoginActionBean.class)
				|| actionBeanClass.equals(CreateAccountActionBean.class));
	}

	/** 
     * Returns <code>true</code> if the user is logged in. 
     */
    private boolean isLoggedIn(ActionBeanContext ctx) { 
        return ((MwActionBeanContext) ctx).isLoggedIn();
    }
}