package com.googlecode.memwords.web;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.googlecode.memwords.web.util.AjaxUtils;
import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;

/**
 * Sitemesh decorator mapper which excludes Ajax requests
 * @author JB
 * @see AjaxUtils#isAjaxRequest(HttpServletRequest)
 */
public class AjaxDecoratorMapper extends AbstractDecoratorMapper {

    @Override
    public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {
        super.init(config, properties, parent);
    }

    @Override
    public Decorator getDecorator(HttpServletRequest request, Page page) {
        boolean ajax = AjaxUtils.isAjaxRequest(request);
        if (ajax) {
            return getNamedDecorator(request, null);
        }
        else {
            return super.getDecorator(request, page);
        }
    }
}
