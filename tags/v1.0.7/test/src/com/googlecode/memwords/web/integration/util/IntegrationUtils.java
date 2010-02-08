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

    /**
     * The sidebar HTML ID
     */
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
     * <code>NicelyResynchronizingAjaxController</code>. Moreover, all requests
     * sent by the web client also have an additional header "ajaxSync=true", in order for the server
     * to know that Ajax should not be asynchronous, and adapt the Ajax calls in order for them
     * to be synchronized and thus avoid having to wait for background tasks to complete
     * @param ajaxSync whether to sync ajax calls or not
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

    /**
     * Builds an absolute URL (example : http://localhost:8888/Index.action) from a
     * context-relative path (example : /Index.action). The beginning of the URL is by default
     * http://localhost:8888/, except if the system property com.googlecode.memwords.url is
     * set (in which case the value of this property is used)
     * @param path the context-relative path
     * @return the absolute URL
     */
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

    /**
     * Starts a web client and calls the integration tests action bean URL in order
     * to set up the test data in the database
     */
    public static void setUpData() throws Exception {
        WebClient wc = startWebClient();
        Page page = wc.getPage(url("/util/IntegrationTests.action?setUp="));
        assertEquals("OK", page.getWebResponse().getContentAsString());
    }

    /**
     * Performs basic tests on an HTML page : page complete, no ??? found in the page text,
     * all IDs unique, etc.
     * @param page the page to check
     */
    public static void testBasics(HtmlPage page) {
        String content = page.getWebResponse().getContentAsString();
        assertFalse("There is probably an i18n problem : ??? found in page",
                    content.contains("???"));
        assertTrue("The page is not complete : it doesn't contain </html>",
                   content.contains("</html>"));
        WebAssert.assertAllAccessKeyAttributesUnique(page);
        WebAssert.assertAllIdAttributesUnique(page);
    }

    /**
     * Gets a side bar link
     * @param page the page
     * @param text the text of the link
     * @return the link
     * @throws ElementNotFoundException if the link is not found
     */
    public static HtmlAnchor getSideBarLink(HtmlPage page, String text) {
        HtmlDivision sideBar = page.getHtmlElementById(SIDE_BAR_HTML_ID);
        return getFirstLinkByText(sideBar, text);
    }

    /**
     * Tests that the title of the given page is "MemWords - " followed by the expected title
     */
    public static void testTitle(HtmlPage page, String expectedTitle) {
        assertEquals("MemWords - " + expectedTitle, page.getTitleText());
    }

    /**
     * Checks that the given error exists in the message panel
     * @param page the page
     * @param expectedError the text of the expected error
     */
    public static void testErrorExists(HtmlPage page, String expectedError) {
        HtmlDivision messagesDiv = page.getHtmlElementById("messages");
        List<HtmlElement> errors = messagesDiv.getElementsByAttribute("ul", "class", "errors");
        assertFalse("No error found", errors.isEmpty());
        boolean found = errorOrMessageExists(errors, expectedError);
        assertTrue("This error wasn't found", found);
    }

    /**
     * Checks that the given success message exists in the message panel
     * @param page the page
     * @param expectedMessage the text of the expected message
     */
    public static void testMessageExists(HtmlPage page, String expectedMessage) {
        HtmlDivision messagesDiv = page.getHtmlElementById("messages");
        List<HtmlElement> messages = messagesDiv.getElementsByAttribute("ul", "class", "messages");
        assertFalse("No message found", messages.isEmpty());
        boolean found = errorOrMessageExists(messages, expectedMessage);
        assertTrue("This message wasn't found", found);
    }

    /**
     * Uses the given web client to log in.
     * @param wc the web client
     * @return the page returned after the login
     */
    public static HtmlPage login(WebClient wc) throws Exception {
        HtmlPage page = wc.getPage(url("/account/Login.action"));
        HtmlForm form = page.getHtmlElementById("loginForm");
        form.getInputByName("userId").type("test");
        form.getInputByName("masterPassword").type("test");
        page = form.getInputByValue("Log in").click();
        return page;
    }

    /**
     * Gets the first link which has the given text inside the given element
     * @param e the element which contains the searched link
     * @param text the text of the link
     * @return the found link, or <code>null</code> if not found
     * @throws ElementNotFoundException if the link is not found
     */
    public static HtmlAnchor getFirstLinkByText(HtmlElement e, String text) {
        List<HtmlAnchor> links = e.getHtmlElementsByTagName("a");
        for (HtmlAnchor link : links) {
            if (text.equals(link.asText())) {
                return link;
            }
        }
        throw new ElementNotFoundException("a", "text", text);
    }

    /**
     * Gets the first link which has the given title attribute value inside the given element
     * @param e the element which contains the searched link
     * @param title the title attribute of the link
     * @return the found link
     * @throws ElementNotFoundException if the link is not found
     */
    public static HtmlAnchor getFirstLinkByTitle(HtmlElement e, String title) {
        List<HtmlAnchor> links = e.getHtmlElementsByTagName("a");
        for (HtmlAnchor link : links) {
            if (title.equals(link.getAttribute("title"))) {
                return link;
            }
        }
        throw new ElementNotFoundException("a", "title", title);
    }

    /**
     * Tests that there is no element with the given element ID in the given page
     * @param page the page
     * @param elementId the ID of the element
     */
    public static void testElementNotPresent(HtmlPage page, String elementId) {
        WebAssert.assertElementNotPresent(page, elementId);
    }

    /**
     * Gets the radio input which has the given name attribute and the given value attribute
     * inside the given form element
     * @param form the form
     * @param name the name attribute
     * @param value the value attribute
     * @return the radio
     * @throws ElementNotFoundException if the radio is not found
     */
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
