package com.googlecode.memwords.web.util;

import static org.junit.Assert.*;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import org.junit.Test;

public class AjaxUtilsTest {

    @Test
    public void testIsAjaxRequest() {
        MockHttpServletRequest request =
            new MockHttpServletRequest("", "/foo.action");
        assertFalse(AjaxUtils.isAjaxRequest(request));
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        assertTrue(AjaxUtils.isAjaxRequest(request));

        request =
            new MockHttpServletRequest("", "/foo.action");
        request.getParameterMap().put("hello", new String[] {""});
        assertFalse(AjaxUtils.isAjaxRequest(request));
        request.getParameterMap().put("ajaxFind", new String[] {""});
        assertTrue(AjaxUtils.isAjaxRequest(request));
    }

}
