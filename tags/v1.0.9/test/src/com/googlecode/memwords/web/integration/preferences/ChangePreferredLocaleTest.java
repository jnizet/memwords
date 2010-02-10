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
        WebClient wc = startWebClient(false);
        HtmlPage page = goToChangePreferredLocalePage(wc);
        testBasics(page);
        testTitle(page, "Preferences");
    }

    @Test
    public void testPageWithoutJavascript() throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToChangePreferredLocalePage(wc);
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
        testFormSubmission(true);
    }

    @Test
    public void testFormSubmissionWithoutJavascript() throws Exception {
        testFormSubmission(false);
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(withJavascript);
        HtmlPage page = goToChangePreferredLocalePage(wc);
        HtmlForm form = page.getHtmlElementById("changePreferredLocaleForm");
        page = form.getInputByValue("Cancel").click();
        wc.waitForBackgroundJavaScript(10000L);
        WebAssert.assertElementNotPresent(page, "changePreferredLocaleForm");
        testTitle(page, "Preferences");
    }

    public void testFormSubmission(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(withJavascript);
        HtmlPage page = goToChangePreferredLocalePage(wc);
        HtmlForm form = page.getHtmlElementById("changePreferredLocaleForm");
        assertEquals("None (automatic detection)", form.getSelectByName("locale").asText());
        form.getSelectByName("locale").setSelectedAttribute("fr", true);
        page = form.getInputByValue("Choose").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Pr\u00e9f\u00e9rences");
        testMessageExists(page, "La langue pr\u00e9f\u00e9r\u00e9e a \u00e9t\u00e9 modifi\u00e9e.");
        WebAssert.assertElementNotPresent(page, "changePreferredLocaleForm");

        page = page.getAnchorByText("Choisir votre langue pr\u00e9f\u00e9r\u00e9e").click();
        wc.waitForBackgroundJavaScript(10000L);
        form = page.getHtmlElementById("changePreferredLocaleForm");
        assertEquals("fran\u00e7ais", form.getSelectByName("locale").asText());
        form.getSelectByName("locale").setSelectedAttribute("", true);
        page = form.getInputByValue("Choisir").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Preferences");
        testMessageExists(page, "The preferred language has been changed.");
    }

    private HtmlPage goToChangePreferredLocalePage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/Preferences.action"));
        HtmlAnchor link = page.getAnchorByText("Choose your preferred language");
        HtmlPage result = link.click();
        wc.waitForBackgroundJavaScript(10000L);
        return result;
    }
}
