package com.googlecode.memwords.web;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

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
    public void setContext(ActionBeanContext context) {
        this.context = (MwActionBeanContext) context;
    }
}
