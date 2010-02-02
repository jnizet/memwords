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
 * Integration tests for the Change password preferences page
 * @author JB
 */
public class ChangePasswordPreferencesTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient(false);
        HtmlPage page = goToChangePasswordPreferencesPage(wc);
        testBasics(page);
        testTitle(page, "Preferences");
    }

    @Test
    public void testPageWithoutJavascript() throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToChangePasswordPreferencesPage(wc);
        testBasics(page);
        testTitle(page, "Password preferences");
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
        HtmlPage page = goToChangePasswordPreferencesPage(wc);
        HtmlForm form = page.getHtmlElementById("changePasswordPreferencesForm");
        assertTrue(getRadioByNameAndValue(form, "unmasked", "false").isChecked());
        getRadioByNameAndValue(form, "unmasked", "true").click();
        page = form.getInputByValue("Change").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Preferences");
        testMessageExists(page, "The password preferences have been changed.");
        WebAssert.assertElementNotPresent(page, "changePasswordPreferencesForm");

        page = page.getAnchorByText("Password preferences").click();
        wc.waitForBackgroundJavaScript(10000L);
        form = page.getHtmlElementById("changePasswordPreferencesForm");
        assertTrue(getRadioByNameAndValue(form, "unmasked", "true").isChecked());
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(withJavascript);
        HtmlPage page = goToChangePasswordPreferencesPage(wc);
        HtmlForm form = page.getHtmlElementById("changePasswordPreferencesForm");
        page = form.getInputByValue("Cancel").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Preferences");
        WebAssert.assertElementNotPresent(page, "changePasswordPreferencesForm");
    }

    private HtmlPage goToChangePasswordPreferencesPage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/Preferences.action"));
        HtmlAnchor link = page.getAnchorByText("Password preferences");
        HtmlPage result = link.click();
        wc.waitForBackgroundJavaScript(10000L);
        return result;
    }
}
