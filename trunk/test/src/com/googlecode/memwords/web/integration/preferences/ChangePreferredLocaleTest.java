package com.googlecode.memwords.web.integration.preferences;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the Change preferred locale page
 * @author JB
 */
public class ChangePreferredLocaleTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangePreferredLocale.action"));
        testBasics(page);
        testTitle(page, "Choose preferred language");
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
        HtmlPage page = wc.getPage(url("/preferences/ChangePreferredLocale.action"));
        HtmlForm form = page.getHtmlElementById("changePreferredLocaleForm");
        assertEquals("None (automatic detection)", form.getSelectByName("locale").asText());
        form.getSelectByName("locale").setSelectedAttribute("fr", true);
        page = form.getInputByValue("Choose").click();
        testTitle(page, "Pr\u00e9f\u00e9rences");
        testMessageExists(page, "La langue pr\u00e9f\u00e9r\u00e9e a \u00e9t\u00e9 modifi\u00e9e.");
        page = wc.getPage(url("/preferences/ChangePreferredLocale.action"));
        form = page.getHtmlElementById("changePreferredLocaleForm");
        assertEquals("fran\u00e7ais", form.getSelectByName("locale").asText());
        form.getSelectByName("locale").setSelectedAttribute("", true);
        page = form.getInputByValue("Choisir").click();
        testTitle(page, "Preferences");
        testMessageExists(page, "The preferred language has been changed.");
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(withJavascript);
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangePreferredLocale.action"));
        HtmlForm form = page.getHtmlElementById("changePreferredLocaleForm");
        page = form.getInputByValue("Cancel").click();
        testTitle(page, "Preferences");
    }
}
