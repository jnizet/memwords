package com.googlecode.memwords.web.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.memwords.web.util.UserAgent.BrowserType;
import com.googlecode.memwords.web.util.UserAgent.OperatingSystem;

/**
 * Tests for the {@link UserAgent} class
 * @author JB
 */
public class UserAgentTest {
    @Test
    public void testDetectBrowserType() {
        assertEquals(BrowserType.CHROME,
                     UserAgent.detectBrowserType("Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/532.8 (KHTML, like Gecko) Chrome/4.0.277.0 Safari/532.8"));
        assertEquals(BrowserType.CHROME,
                     UserAgent.detectBrowserType("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.0 Safari/532.5"));
        assertEquals(BrowserType.FIREFOX,
                     UserAgent.detectBrowserType("Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.9.0.2) Gecko/20121223 Ubuntu/9.25 (jaunty) Firefox/3.8"));
        assertEquals(BrowserType.FIREFOX,
                     UserAgent.detectBrowserType("Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.9.0.2) Gecko/20121223 Ubuntu/9.25 (jaunty) Firefox/3.8"));
        assertEquals(BrowserType.FIREFOX,
                     UserAgent.detectBrowserType("Mozilla/5.0 (Windows; U; Windows NT 6.1; ru-RU; rv:1.9.2) Gecko/20100105 MRA 5.6 (build 03278) Firefox/3.6 (.NET CLR 3.5.30729)"));
        assertEquals(BrowserType.FIREFOX,
                     UserAgent.detectBrowserType("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; fr; rv:1.9.1b4) Gecko/20090423 Firefox/3.5b4"));
        assertEquals(BrowserType.IE,
                     UserAgent.detectBrowserType("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.2; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)"));
        assertEquals(BrowserType.IE,
                     UserAgent.detectBrowserType("Mozilla/5.0 (MSIE 7.0; Macintosh; U; SunOS; X11; gu; SV1; InfoPath.2; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648)"));
        assertEquals(BrowserType.OPERA,
                     UserAgent.detectBrowserType("Opera/9.64(Windows NT 5.1; U; en) Presto/2.1.1"));
        assertEquals(BrowserType.OPERA,
                     UserAgent.detectBrowserType("Opera/9.64 (X11; Linux x86_64; U; pl) Presto/2.1.1"));
        assertEquals(BrowserType.SAFARI,
                     UserAgent.detectBrowserType("Mozilla/5.0 (Windows; U; Windows NT 6.0; en-us) AppleWebKit/531.9 (KHTML, like Gecko) Version/4.0.3 Safari/531.9"));
        assertEquals(BrowserType.SAFARI,
                     UserAgent.detectBrowserType("Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_5_8; en-us) AppleWebKit/532.0+ (KHTML, like Gecko) Version/4.0.3 Safari/531.9.2009"));
        assertEquals(BrowserType.UNKNOWN,
                     UserAgent.detectBrowserType("Mozilla/5.0 (compatible; Konqueror/4.3; Linux) KHTML/4.3.1 (like Gecko) Fedora/4.3.1-3.fc11"));
        assertTrue(BrowserType.UNKNOWN.isUnknown());
        assertFalse(BrowserType.IE.isUnknown());
    }

    @Test
    public void testDetectOperatingSystem() {
        assertEquals(OperatingSystem.LINUX,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/532.8 (KHTML, like Gecko) Chrome/4.0.277.0 Safari/532.8"));
        assertEquals(OperatingSystem.WINDOWS,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.0 Safari/532.5"));
        assertEquals(OperatingSystem.LINUX,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.9.0.2) Gecko/20121223 Ubuntu/9.25 (jaunty) Firefox/3.8"));
        assertEquals(OperatingSystem.LINUX,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.9.0.2) Gecko/20121223 Ubuntu/9.25 (jaunty) Firefox/3.8"));
        assertEquals(OperatingSystem.WINDOWS,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (Windows; U; Windows NT 6.1; ru-RU; rv:1.9.2) Gecko/20100105 MRA 5.6 (build 03278) Firefox/3.6 (.NET CLR 3.5.30729)"));
        assertEquals(OperatingSystem.MAC,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; fr; rv:1.9.1b4) Gecko/20090423 Firefox/3.5b4"));
        assertEquals(OperatingSystem.WINDOWS,
                     UserAgent.detectOperatingSystem("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.2; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0)"));
        assertEquals(OperatingSystem.MAC,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (MSIE 7.0; Macintosh; U; SunOS; X11; gu; SV1; InfoPath.2; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648)"));
        assertEquals(OperatingSystem.WINDOWS,
                     UserAgent.detectOperatingSystem("Opera/9.64(Windows NT 5.1; U; en) Presto/2.1.1"));
        assertEquals(OperatingSystem.LINUX,
                     UserAgent.detectOperatingSystem("Opera/9.64 (X11; Linux x86_64; U; pl) Presto/2.1.1"));
        assertEquals(OperatingSystem.WINDOWS,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (Windows; U; Windows NT 6.0; en-us) AppleWebKit/531.9 (KHTML, like Gecko) Version/4.0.3 Safari/531.9"));
        assertEquals(OperatingSystem.MAC,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_5_8; en-us) AppleWebKit/532.0+ (KHTML, like Gecko) Version/4.0.3 Safari/531.9.2009"));
        assertEquals(OperatingSystem.LINUX,
                     UserAgent.detectOperatingSystem("Mozilla/5.0 (compatible; Konqueror/4.3; Linux) KHTML/4.3.1 (like Gecko) Fedora/4.3.1-3.fc11"));
        assertEquals(OperatingSystem.UNKNOWN,
                     UserAgent.detectOperatingSystem("Mozilla/4.8 [en] (X11; U; HP-UX B.11.00 9000/785)"));
        assertTrue(OperatingSystem.UNKNOWN.isUnknown());
        assertFalse(OperatingSystem.LINUX.isUnknown());
    }
}
