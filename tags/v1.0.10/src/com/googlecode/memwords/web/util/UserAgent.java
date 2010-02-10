package com.googlecode.memwords.web.util;

/**
 * Utility class to extract information from a user agent string
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

        /**
         * The name of the browser
         */
        private String name;

        /**
         * The context-relative image URL for the browser
         */
        private String imageUrl;

        /**
         * Constructor
         * @param name the name of the browser
         * @param imageUrl the context-relative image URL for the browser
         */
        BrowserType(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        /**
         * Gets the context-relative image URL for the browser
         * @return the context-relative image URL for the browser
         */
        public String getImageUrl() {
            return this.imageUrl;
        }

        /**
         * Gets the name of the browser
         * @return the name of the browser
         */
        public String getName() {
            return this.name;
        }

        /**
         * Indicates if the browser is unknown
         * @return <code>true</code> if the browser is unknown, <code>false</code> otherwise
         */
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

        /**
         * The name of the OS
         */
        private String name;

        /**
         * The context-relative image URL for the OS
         */
        private String imageUrl;

        /**
         * Constructor
         * @param name the name of the OS
         * @param imageUrl the context-relative image URL for the OS
         */
        OperatingSystem(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        /**
         * Gets the context-relative image URL for the OS
         * @return the context-relative image URL for the OS
         */
        public String getImageUrl() {
            return this.imageUrl;
        }

        /**
         * Gets the name of the OS
         * @return the name of the OS
         */
        public String getName() {
            return this.name;
        }

        /**
         * Indicates if the OS is unknown
         * @return <code>true</code> if the OS is unknown, <code>false</code> otherwise
         */
        public boolean isUnknown() {
            return this == UNKNOWN;
        }
    }

    /**
     * Constructor. Private to prevent unnecessary instantiation.
     */
    private UserAgent() {
    }

    /**
     * Detects the browser type from a user agent string
     * @param userAgentString the user agent string
     * @return the detected browser type
     */
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

    /**
     * Detects the operating system from a user agent string
     * @param userAgentString the user agent string
     * @return the detected operating system
     */
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
