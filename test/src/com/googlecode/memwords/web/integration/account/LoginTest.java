package com.googlecode.memwords.web.integration.account;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.googlecode.memwords.web.integration.AuthenticationInterceptorTest;

/**
 * Integration tests for the login page.
 * Note that the login form submission is already tested in {@link AuthenticationInterceptorTest}
 * @author JB
 */
public class LoginTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/account/Login.action"));
        testBasics(page);
        testTitle(page, "Log in");
    }

    @Test
    public void testValidation() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/account/Login.action"));
        HtmlForm form = page.getHtmlElementById("loginForm");
        page = form.getInputByValue("Log in").click();
        testTitle(page, "Log in");
        testErrorExists(page, "User ID is a required field");
        testErrorExists(page, "Master password is a required field");
        form = page.getHtmlElementById("loginForm");
        form.getInputByName("userId").type("test");
        form.getInputByName("masterPassword").type("hello");
        page = form.getInputByValue("Log in").click();
        testErrorExists(page, "Login failed. Try again.");
        form = page.getHtmlElementById("loginForm");
        assertEquals("test", form.getInputByName("userId").getValueAttribute());
        assertEquals("", form.getInputByName("masterPassword").getValueAttribute());
    }

    @Test
    public void testSidebarLinks() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/"));
        HtmlAnchor cardsLink = getSideBarLink(page, "Cards");
        HtmlPage cardsPage = cardsLink.click();
        testTitle(cardsPage, "Cards");
        HtmlAnchor logoutLink = getSideBarLink(page, "Log out");
        HtmlPage indexPage = logoutLink.click();
        testTitle(indexPage, "Welcome");
        // try to re-click on the cards link : the login page should be displayed
        HtmlPage loginPage = cardsLink.click();
        testTitle(loginPage, "Log in");
    }
}
