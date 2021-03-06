package com.googlecode.memwords.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.googlecode.memwords.web.account.CreateAccountActionBean;
import com.googlecode.memwords.web.account.LoginActionBean;
import com.googlecode.memwords.web.tools.ToolsActionBean;
import com.googlecode.memwords.web.util.AjaxUtils;
import com.googlecode.memwords.web.util.IntegrationTestsActionBean;

/**
 * Stripes interceptor which restores the secret key, if found in a cookie, into the user
 * information (see {@link MwActionBeanContext#loadUserInformation()}.
 * It then verifies that the user is authenticated.
 * If the user is authenticated, the filter soesn't do anything.
 * If the request is for the index, create account, or login page, it doesn't do
 * anything.
 * If the request is a GET request and not an Ajax request, then a redirect is sent
 * to the login page, which will then redirect to the original request URL.
 * If the request is a POST request and not an Ajax request, then a redirect is sent
 * to the login page, which will then redirect to the index page.
 * If the request is an AJAX request, then a response with the status 403 (Forbidden) is
 * sent, so that the client ajax handler redirects to the login page.
 * @author JB
 */
@Intercepts(LifecycleStage.HandlerResolution)
public class AuthenticationInterceptor implements Interceptor {

    /**
     * Intercepts the request, and returns the appropriate resolution.
     * See class documentation for more details
     * @param ctx the execution context
     * @return the appropriate resolution
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Resolution intercept(ExecutionContext ctx) throws Exception {
        MwActionBeanContext actionBeanContext = (MwActionBeanContext) ctx.getActionBeanContext();
        actionBeanContext.loadUserInformation();

        if (isPermittedWithoutLogin(ctx.getActionBean())) {
            return ctx.proceed();
        }
        else if (!actionBeanContext.isLoggedIn()) {
            if (AjaxUtils.isAjaxRequest(actionBeanContext.getRequest())) {
                return new ErrorResolution(HttpServletResponse.SC_FORBIDDEN, null);
            }
            addRequestedUrlToContext(actionBeanContext);
            return new ForwardResolution(LoginActionBean.class);
        }
        else {
            return ctx.proceed();
        }
    }

    /**
     * Adds the requested URL to the context if the request is a GET request
     * @param ctx the context to which the requested URL must be added
     */
    private void addRequestedUrlToContext(MwActionBeanContext ctx) {
        HttpServletRequest request = ctx.getRequest();
        if ("GET".equals(request.getMethod())) {
            String url = request.getRequestURL().toString();
            if (request.getQueryString() != null) {
                url = url + "?" + request.getQueryString();
            }
            ctx.setRequestedUrl(url);
        }
    }

    /**
     * Determines if login is needed in order to access the given action bean
     * @param actionBean the action bean being requested
     * @return <code>true</code> if the action bean may be accessed without login,
     * <code>false</code> otherwise
     */
    private boolean isPermittedWithoutLogin(ActionBean actionBean) {
        Class<?> actionBeanClass = actionBean.getClass();
        return actionBeanClass.equals(IndexActionBean.class)
               || actionBeanClass.equals(LoginActionBean.class)
               || actionBeanClass.equals(CreateAccountActionBean.class)
               || actionBeanClass.equals(IntegrationTestsActionBean.class)
               || actionBeanClass.equals(ScreenshotsActionBean.class)
               || actionBeanClass.equals(ToolsActionBean.class);
    }
}