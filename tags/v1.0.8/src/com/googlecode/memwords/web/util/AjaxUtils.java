package com.googlecode.memwords.web.util;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class to handle Ajax-related features.
 * @author JB
 */
public final class AjaxUtils {

    /**
     * Constructor. Private to prevent unnecessary instantiation.
     */
    private AjaxUtils() {
    }

    /**
     * Determines if the given request is an AJAX request.
     * A request is an ajax request if the X-Requested-With header is set and its value
     * is XMLHttpRequest. Since it's not sure if this header is standard and always set,
     * a request containing a parameter name starting with ajax is also considered as an AJAX request.
     * @param request the request to test
     * @return <code>true</code> if the request is an AJAX request, <code>false</code> otherwise.
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String pageHead = request.getHeader("X-Requested-With");

        if ("XMLHttpRequest".equalsIgnoreCase(pageHead)) {
            return true;
        }

        // safe check
        boolean ajaxFound = false;
        for (Iterator<?> it = request.getParameterMap().keySet().iterator(); it.hasNext() && !ajaxFound; ) {
            String paramName = (String) it.next();
            if (paramName.startsWith("ajax")) {
                ajaxFound = true;
            }
        }
        return ajaxFound;
    }
}
