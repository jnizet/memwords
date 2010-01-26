package com.googlecode.memwords.web.integration.preferences;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the Change master password page
 * @author JB
 */
public class ChangeMasterPasswordTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangeMasterPassword.action"));
        testBasics(page);
        testTitle(page, "Change master password");
    }

    @Test
    public void testValidation() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangeMasterPassword.action"));
        HtmlForm form = page.getHtmlElementById("changeMasterPasswordForm");
        page = form.getInputByValue("Change").click();
        testTitle(page, "Change master password");
        testErrorExists(page, "Current master password is a required field");
        testErrorExists(page, "New master password is a required field");
        testErrorExists(page, "New master password confirmation is a required field");

        form = page.getHtmlElementById("changeMasterPasswordForm");
        form.getInputByName("currentPassword").type("test2");
        form.getInputByName("newPassword").type("abc");
        form.getInputByName("newPasswordConfirmation").type("def");
        page = form.getInputByValue("Change").click();
        testTitle(page, "Change master password");
        testErrorExists(page, "The current password is not the right one");
        testErrorExists(page, "New master password must be at least 4 characters long");
        testErrorExists(page, "The new master password confirmation must be identical to the new master password");
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
        HtmlPage page = wc.getPage(url("/preferences/ChangeMasterPassword.action"));
        HtmlForm form = page.getHtmlElementById("changeMasterPasswordForm");
        form.getInputByName("currentPassword").type("test");
        form.getInputByName("newPassword").type("test2");
        form.getInputByName("newPasswordConfirmation").type("test2");
        page = form.getInputByValue("Change").click();
        testTitle(page, "Preferences");
        testMessageExists(page, "The master password has been changed.");
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(withJavascript);
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/ChangeMasterPassword.action"));
        HtmlForm form = page.getHtmlElementById("changeMasterPasswordForm");
        page = form.getInputByValue("Cancel").click();
        testTitle(page, "Preferences");
    }
}
