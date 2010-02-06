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
 * Integration tests for the Change password generation preferences page
 * @author JB
 */
public class ChangePasswordGenerationPreferencesTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient(false);
        HtmlPage page = goToChangePasswordGenerationPreferencesPage(wc);
        testBasics(page);
        testTitle(page, "Preferences");
    }

    @Test
    public void testPageWithoutJavascript() throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToChangePasswordGenerationPreferencesPage(wc);
        testBasics(page);
        testTitle(page, "Random password generation preferences");
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

    @Test
    public void testJavaScriptChangeHandler() throws Exception {
        WebClient wc = startWebClient(false);
        HtmlPage page = goToChangePasswordGenerationPreferencesPage(wc);
        HtmlForm form = page.getHtmlElementById("changePasswordGenerationPreferencesForm");
        String xmlBefore = page.getHtmlElementById("strength").asXml();
        form.getInputByName("lowerCaseLettersIncluded").setChecked(false);
        form.getInputByName("upperCaseLettersIncluded").setChecked(false);
        form.getInputByName("digitsIncluded").setChecked(false);
        form.getInputByName("specialCharactersIncluded").setChecked(false);
        wc.waitForBackgroundJavaScript(10000L);
        assertEquals("disabled", page.getHtmlElementById("submitButton").getAttribute("disabled"));
        String xmlAfter = page.getHtmlElementById("strength").asXml();
        assertFalse(xmlBefore.equals(xmlAfter));
    }

    @Test
    public void testValidation() throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToChangePasswordGenerationPreferencesPage(wc);
        HtmlForm form = page.getHtmlElementById("changePasswordGenerationPreferencesForm");
        form.getInputByName("lowerCaseLettersIncluded").setChecked(false);
        form.getInputByName("upperCaseLettersIncluded").setChecked(false);
        form.getInputByName("digitsIncluded").setChecked(false);
        form.getInputByName("specialCharactersIncluded").setChecked(false);
        page = form.getInputByValue("Change").click();
        testTitle(page, "Random password generation preferences");
        testErrorExists(page, "At least one kind of characters must be checked.");
    }

    private void testFormSubmission(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(withJavascript);
        HtmlPage page = goToChangePasswordGenerationPreferencesPage(wc);
        HtmlForm form = page.getHtmlElementById("changePasswordGenerationPreferencesForm");
        assertEquals("8", form.getSelectByName("length").getSelectedOptions().get(0).getValueAttribute());
        assertTrue(form.getInputByName("lowerCaseLettersIncluded").isChecked());
        assertTrue(form.getInputByName("upperCaseLettersIncluded").isChecked());
        assertTrue(form.getInputByName("digitsIncluded").isChecked());
        assertTrue(form.getInputByName("specialCharactersIncluded").isChecked());
        form.getSelectByName("length").setSelectedAttribute("10", true);
        form.getInputByName("lowerCaseLettersIncluded").click();
        form.getInputByName("digitsIncluded").click();

        page = form.getInputByValue("Change").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Preferences");
        testMessageExists(page, "The random password generation preferences have been changed.");
        WebAssert.assertElementNotPresent(page, "changePasswordGenerationPreferencesForm");

        page = page.getAnchorByText("Random password generation preferences").click();
        wc.waitForBackgroundJavaScript(10000L);
        form = page.getHtmlElementById("changePasswordGenerationPreferencesForm");
        assertEquals("10", form.getSelectByName("length").getSelectedOptions().get(0).getValueAttribute());
        assertFalse(form.getInputByName("lowerCaseLettersIncluded").isChecked());
        assertTrue(form.getInputByName("upperCaseLettersIncluded").isChecked());
        assertFalse(form.getInputByName("digitsIncluded").isChecked());
        assertTrue(form.getInputByName("specialCharactersIncluded").isChecked());
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient(false);
        wc.setJavaScriptEnabled(withJavascript);
        HtmlPage page = goToChangePasswordGenerationPreferencesPage(wc);
        HtmlForm form = page.getHtmlElementById("changePasswordGenerationPreferencesForm");
        page = form.getInputByValue("Cancel").click();
        wc.waitForBackgroundJavaScript(10000L);
        testTitle(page, "Preferences");
        WebAssert.assertElementNotPresent(page, "changePasswordGenerationPreferencesForm");
    }

    private HtmlPage goToChangePasswordGenerationPreferencesPage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/Preferences.action"));
        HtmlAnchor link = page.getAnchorByText("Random password generation preferences");
        HtmlPage result = link.click();
        wc.waitForBackgroundJavaScript(10000L);
        return result;
    }
}
