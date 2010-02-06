package com.googlecode.memwords.web.integration;


import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
/**
 * Integration test for the index page
 * @author JB
 */
public class IndexTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/"));
        testBasics(page);
        testTitle(page, "Remembers your passwords");
    }

    @Test
    public void testSideBarLinks() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/"));
        HtmlAnchor loginLink = getSideBarLink(page, "Log in");
        HtmlPage loginPage = loginLink.click();
        testTitle(loginPage, "Log in");

        HtmlAnchor createAccountLink = getSideBarLink(page, "Create account");
        HtmlPage createAccountPage = createAccountLink.click();
        testTitle(createAccountPage, "Create an account");
    }

    @Test
    public void testScreenshots() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/"));
        HtmlAnchor screenshotsLink = page.getAnchorByText("Look at screenshots!");
        HtmlPage screenshotsPage = screenshotsLink.click();
        testTitle(screenshotsPage, "Screenshots");
        testBasics(screenshotsPage);
    }
}
