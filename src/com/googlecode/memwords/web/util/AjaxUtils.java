package com.googlecode.memwords.web.util;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

public final class AjaxUtils {
	private AjaxUtils() {
	}
	
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
