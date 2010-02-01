package com.googlecode.memwords.web.integration.util;

import static org.junit.Assert.*;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;

/**
 * Utility methods for integration (HtmlUnit) tests
 * @author JB
 */
public final class IntegrationUtils {

    public static final String SIDE_BAR_HTML_ID = "sidebar";

    private IntegrationUtils() {
    }

    /**
     * Starts and returns a web client, using {@link #startWebClient(boolean) startWebClient(true)}.
     * @return the web client
     */
    public static WebClient startWebClient() {
        return startWebClient(true);
    }

    /**
     * Starts and returns a web client siumulating Firefox 3, with CSS disabled.
     * If <code>ajaxSync</code> is <code>true</code>, then the web client is configured with a
     * <code>NicelyResynchronizingAjaxController</code>. All requests
     * sent by the web client also have an additional header "ajaxSync=true", in order for the server
     * to know that Ajax should not be asynchronous, and adapt the Ajax calls in order for them
     * to be synchronized and thus avoid having to wait for background tasks to complete
     * @return the web client
     */
    public static WebClient startWebClient(boolean ajaxSync) {
        WebClient wc = new WebClient(BrowserVersion.FIREFOX_3);
        wc.setCssEnabled(false);
        if (ajaxSync) {
            wc.addRequestHeader("ajaxSync", "true");
            wc.setAjaxController(new NicelyResynchronizingAjaxController());
        }
        return wc;
    }

    public static String url(String path) {
        String baseUrl = System.getProperty("com.googlecode.memwords.url");
        if (baseUrl == null) {
            baseUrl = "http://localhost:8888/";
        }
        else if (!baseUrl.endsWith("/")) {
            baseUrl += '/';
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return baseUrl + path;
    }

    public static void setUpData() throws Exception {
        WebClient wc = startWebClient();
        Page page = wc.getPage(url("/util/IntegrationTests.action?setUp="));
        assertEquals("OK", page.getWebResponse().getContentAsString());
    }

    public static void testBasics(HtmlPage page) {
        String content = page.getWebResponse().getContentAsString();
        assertFalse("There is probably an i18n problem : ??? found in page",
                    content.contains("???"));
        assertTrue("The page is not complete : it doesn't contain </html>",
                   content.contains("</html>"));
        WebAssert.assertAllAccessKeyAttributesUnique(page);
        WebAssert.assertAllIdAttributesUnique(page);
    }

    public static HtmlAnchor getSideBarLink(HtmlPage page, String text) {
        HtmlDivision sideBar = page.getHtmlElementById(SIDE_BAR_HTML_ID);
        return getFirstLinkByText(sideBar, text);
    }

    public static void testTitle(HtmlPage page, String expectedTitle) {
        assertEquals("MemWords - " + expectedTitle, page.getTitleText());
    }

    public static void testErrorExists(HtmlPage page, String expectedError) {
        HtmlDivision messagesDiv = page.getHtmlElementById("messages");
        List<HtmlElement> errors = messagesDiv.getElementsByAttribute("ul", "class", "errors");
        assertFalse("No error found", errors.isEmpty());
        boolean found = errorOrMessageExists(errors, expectedError);
        assertTrue("This error wasn't found", found);
    }

    public static void testMessageExists(HtmlPage page, String expectedMessage) {
        HtmlDivision messagesDiv = page.getHtmlElementById("messages");
        List<HtmlElement> messages = messagesDiv.getElementsByAttribute("ul", "class", "messages");
        assertFalse("No message found", messages.isEmpty());
        boolean found = errorOrMessageExists(messages, expectedMessage);
        assertTrue("This message wasn't found", found);
    }

    public static HtmlPage login(WebClient wc) throws Exception {
        HtmlPage page = wc.getPage(url("/account/Login.action"));
        HtmlForm form = page.getHtmlElementById("loginForm");
        form.getInputByName("userId").type("test");
        form.getInputByName("masterPassword").type("test");
        page = form.getInputByValue("Log in").click();
        testTitle(page, "Cards");
        return page;
    }

    public static HtmlAnchor getFirstLinkByText(HtmlElement e, String text) {
        List<HtmlAnchor> links = e.getHtmlElementsByTagName("a");
        for (HtmlAnchor link : links) {
            if (text.equals(link.asText())) {
                return link;
            }
        }
        return null;
    }

    public static HtmlAnchor getFirstLinkByTitle(HtmlElement e, String title) {
        List<HtmlAnchor> links = e.getHtmlElementsByTagName("a");
        for (HtmlAnchor link : links) {
            if (title.equals(link.getAttribute("title"))) {
                return link;
            }
        }
        return null;
    }

    public static void testElementNotPresent(HtmlPage page, String elementId) {
        WebAssert.assertElementNotPresent(page, elementId);
    }

    public static HtmlRadioButtonInput getRadioByNameAndValue(HtmlForm form, String name, String value) {
        List<HtmlInput> radios = form.getInputsByName(name);
        for (HtmlInput radio : radios) {
            if (radio instanceof HtmlRadioButtonInput
                && value.equals(radio.getValueAttribute())) {
                return (HtmlRadioButtonInput) radio;
            }
        }
        throw new ElementNotFoundException("input[type=radio, name=" + name + "]", "value", value);
    }

    private static boolean errorOrMessageExists(List<HtmlElement> uls, String expectedErrorOrMessage) {
        // asText is not usable because the messages div is made
        return uls.get(0).asXml().contains(expectedErrorOrMessage);
    }
}
