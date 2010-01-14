package com.googlecode.memwords.web;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public class MwActionBean implements ActionBean {

	private MwActionBeanContext context;
	
	@Override
	public MwActionBeanContext getContext() {
		return context;
	}

	@Override
	public void setContext(ActionBeanContext context) {
		this.context = (MwActionBeanContext) context;
	}
}
