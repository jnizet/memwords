package com.googlecode.memwords.web.integration.account;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the Destroy account page
 * @author JB
 */
public class DestroyAccountTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/account/DestroyAccount.action"));
        testBasics(page);
        testTitle(page, "Destroy account");
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
        HtmlPage page = wc.getPage(url("/account/DestroyAccount.action"));
        HtmlForm form = page.getHtmlElementById("destroyAccountForm");
        page = form.getInputByValue("Yes, destroy").click();
        testTitle(page, "Remembers your passwords");
        assertNotNull(getMenuLink(page, "Log in"));
        testMessageExists(page, "The account has been destroyed.");
    }

    private void testCancel(boolean withJavascript) throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(withJavascript);
        login(wc);
        HtmlPage page = wc.getPage(url("/account/DestroyAccount.action"));
        HtmlForm form = page.getHtmlElementById("destroyAccountForm");
        page = form.getInputByValue("No, cancel").click();
        testTitle(page, "Account");
    }
}
