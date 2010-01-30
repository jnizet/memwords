package com.googlecode.memwords.web.util;

/**
 * IUtility class to extract information from a user agent string
 * @author JB
 */
public final class UserAgent {

    /**
     * The various browser types detected
     */
    public enum BrowserType {
        IE("Internet Explorer", "/img/browser/ie.png"),
        FIREFOX("Firefox", "/img/browser/firefox.png"),
        CHROME("Chrome", "/img/browser/chrome.png"),
        SAFARI("Safari", "/img/browser/safari.png"),
        OPERA("Opera", "/img/browser/opera.png"),
        UNKNOWN("Unknown", "/img/browser/unknown.png");

        private String name;
        private String imageUrl;

        BrowserType(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public String getName() {
            return this.name;
        }

        public boolean isUnknown() {
            return this == UNKNOWN;
        }
    }

    /**
     * The various operating systems detected
     */
    public enum OperatingSystem {
        WINDOWS("Windows", "/img/os/windows.png"),
        MAC("Mac OS", "/img/os/mac.png"),
        LINUX("Linux", "/img/os/linux.png"),
        UNKNOWN("Unknwown", "/img/os/unknown.png");

        private String name;
        private String imageUrl;

        OperatingSystem(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public String getName() {
            return this.name;
        }

        public boolean isUnknown() {
            return this == UNKNOWN;
        }
    }

    private UserAgent() {
    }

    public static BrowserType detectBrowserType(String userAgentString) {
        BrowserType result = BrowserType.UNKNOWN;
        if (userAgentString.indexOf("Firefox") >= 0) {
            result = BrowserType.FIREFOX;
        }
        else if (userAgentString.indexOf("MSIE") >= 0) {
            result = BrowserType.IE;
        }
        else if (userAgentString.indexOf("Chrome") >= 0) {
            result = BrowserType.CHROME;
        }
        else if (userAgentString.indexOf("Opera") >= 0) {
            result = BrowserType.OPERA;
        }
        else if (userAgentString.indexOf("Safari") >= 0) {
            result = BrowserType.SAFARI;
        }
        return result;
    }

    public static OperatingSystem detectOperatingSystem(String userAgentString) {
        OperatingSystem result = OperatingSystem.UNKNOWN;
        if (userAgentString.indexOf("Windows") >= 0) {
            result = OperatingSystem.WINDOWS;
        }
        else if (userAgentString.indexOf("Macintosh") >= 0) {
            result = OperatingSystem.MAC;
        }
        else if (userAgentString.indexOf("Linux") >= 0) {
            result = OperatingSystem.LINUX;
        }
        return result;
    }
}
