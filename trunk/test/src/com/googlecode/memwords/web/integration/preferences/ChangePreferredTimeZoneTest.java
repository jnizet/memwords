package com.googlecode.memwords.web.integration.preferences;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the Change preferred time zone page
 * @author JB
 */
public class ChangePreferredTimeZoneTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient(false);
        HtmlPage page = goToChangePreferredTimeZonePage(wc);
        testBasics(page);
        testTitle(page, "Preferences");
    }

    @Test
    public void testPageWithoutJavascript() throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToChangePreferredTimeZonePage(wc);
        testBasics(page);
        testTitle(page, "Choose time zone");
    }

    @Test
    public void testCancel() throws Exception {
        testCancel(true);
    }

    @Test
    public void testCancelWithoutJavascript() throws Exception {
        testCancel(false);
    }

    @Test
    public void testFormSubmission() throws Exception {
        testFormSubmission(true);
    }

    @Test
    public void testFormSubmissionWithoutJavascript() throws Exception {
        testFormSubmission(false);
    }

    public void testFormSubmission(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(withJavascript);
        HtmlPage page = goToChangePreferredTimeZonePage(wc);
        HtmlForm form = page.getHtmlElementById("changePreferredTimeZoneForm");
        assertEquals("GMT", form.getSelectByName("timeZoneId").asText());
        form.getSelectByName("timeZoneId").setSelectedAttribute("Europe/Paris", true);
        page = form.getInputByValue("Choose").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Preferences");
        testMessageExists(page, "The time zone has been changed.");
        WebAssert.assertElementNotPresent(page, "changePreferredTimeZoneForm");

        page = page.getAnchorByText("Choose your time zone").click();
        wc.waitForBackgroundJavaScript(10000L);
        form = page.getHtmlElementById("changePreferredTimeZoneForm");
        assertEquals("Europe/Paris", form.getSelectByName("timeZoneId").asText());
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(withJavascript);
        HtmlPage page = goToChangePreferredTimeZonePage(wc);
        HtmlForm form = page.getHtmlElementById("changePreferredTimeZoneForm");
        page = form.getInputByValue("Cancel").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Preferences");
        WebAssert.assertElementNotPresent(page, "changePreferredTimeZoneForm");
    }

    private HtmlPage goToChangePreferredTimeZonePage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/Preferences.action"));
        HtmlAnchor link = page.getAnchorByText("Choose your time zone");
        HtmlPage result = link.click();
        wc.waitForBackgroundJavaScript(10000L);
        return result;
    }
}
