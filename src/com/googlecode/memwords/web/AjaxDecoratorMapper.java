package com.googlecode.memwords.web;

import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;

/**
 * Decorator mapper which excludes requests having a parameter name starting with ajax
 * @author JB
 */
public class AjaxDecoratorMapper extends AbstractDecoratorMapper {
	
	@Override
	public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {
		super.init(config, properties, parent);
	}

	@Override
	public Decorator getDecorator(HttpServletRequest request, Page page) {
		boolean ajaxFound = false;
		for (Iterator<?> it = request.getParameterMap().keySet().iterator(); it.hasNext() && !ajaxFound; ) {
			String paramName = (String) it.next();
			if (paramName.startsWith("ajax")) {
				ajaxFound = true;
			}
		}
		if (ajaxFound) {
			return getNamedDecorator(request, null);
		}
		else {
			return super.getDecorator(request, page);
		}
	}
}
