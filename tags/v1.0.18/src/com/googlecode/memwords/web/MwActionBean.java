package com.googlecode.memwords.web;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * Base class for all the action beans of the application.
 * @author JB
 */
public class MwActionBean implements ActionBean {

    /**
     * The action bean context
     */
    private MwActionBeanContext context;

    @Override
    public MwActionBeanContext getContext() {
        return context;
    }

    /**
     * Sets the action bean context, which must be an instance of {@link MwActionBeanContext}
     * @param context the context to set
     */
    @Override
    @SuppressWarnings(value = {"BC_UNCONFIRMED_CAST"},
                      justification = "The context is an instance of MwActionBeanContext, "
                                      + "or the application is not well configured")
    public void setContext(ActionBeanContext context) {
        this.context = (MwActionBeanContext) context;
    }
}
