package com.googlecode.memwords.domain;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class to handle URLs
 * @author JB
 */
public final class UrlUtils {
    /**
     * Constructor. Private to prevent unnecessary instantiations
     */
    private UrlUtils() {
    }

    /**
     * Makes a URL entered by a user absolute. The absolutized URL is <code>null</code> if the URL
     * is blank.
     * It is the same as the URL if it contains "://". And it is equals to the URL prepended with
     * "http://" if it's not null and does not contain "://".
     * @param url the URL to absolutize
     * @return the absolutized URL
     */
    public static String absolutizeUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (url.contains("://")) {
            return url;
        }
        return "http://" + url;
    }
}
