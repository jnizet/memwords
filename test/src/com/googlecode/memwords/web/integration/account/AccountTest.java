package com.googlecode.memwords.web.integration.account;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the account page
 * @author JB
 */
public class AccountTest {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/account/Account.action"));
        testBasics(page);
        testTitle(page, "Account");
    }

    @Test
    public void testLinks() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/account/Account.action"));
        HtmlAnchor changeMasterPasswordLink = page.getAnchorByText("Change your master password");
        HtmlPage changeMasterPasswordPage = changeMasterPasswordLink.click();
        testTitle(changeMasterPasswordPage, "Change master password");
    }
}
