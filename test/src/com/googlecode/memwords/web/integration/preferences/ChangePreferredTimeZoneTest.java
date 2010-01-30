package com.googlecode.memwords.web.integration.preferences;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
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
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangePreferredTimeZone.action"));
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
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangePreferredTimeZone.action"));
        HtmlForm form = page.getHtmlElementById("changePreferredTimeZoneForm");
        assertEquals("GMT", form.getSelectByName("timeZoneId").asText());
        form.getSelectByName("timeZoneId").setSelectedAttribute("Europe/Paris", true);
        page = form.getInputByValue("Choose").click();
        testTitle(page, "Preferences");
        testMessageExists(page, "The time zone has been changed.");
        page = wc.getPage(url("/preferences/ChangePreferredTimeZone.action"));
        form = page.getHtmlElementById("changePreferredTimeZoneForm");
        assertEquals("Europe/Paris", form.getSelectByName("timeZoneId").asText());
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(withJavascript);
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangePreferredTimeZone.action"));
        HtmlForm form = page.getHtmlElementById("changePreferredTimeZoneForm");
        page = form.getInputByValue("Cancel").click();
        testTitle(page, "Preferences");
    }
}
