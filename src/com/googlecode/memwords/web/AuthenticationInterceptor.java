package com.googlecode.memwords.web;

import com.googlecode.memwords.web.account.CreateAccountActionBean;
import com.googlecode.memwords.web.account.LoginActionBean;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
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
            return new ForwardResolution("/notLoggedIn.jsp");
        }
        else {
        	return ctx.proceed();
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
    protected boolean isLoggedIn(ActionBeanContext ctx) { 
        return ((MwActionBeanContext) ctx).isLoggedIn();
    }
}