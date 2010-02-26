package com.googlecode.memwords.web.integration;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration test used to test the authentication filter
 * @author JB
 */
public class AuthenticationInterceptorTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testGetRequest() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));
        testTitle(page, "Log in");
        HtmlPage pageAfterLogin = login(page);
        testTitle(pageAfterLogin, "Create a card");
    }

    @Test
    public void testGetRequestWithQueryString() throws Exception {
        WebClient wc = startWebClient();
        String url = url("/cards/CreateCard.action?name=card4");
        HtmlPage page = wc.getPage(url);
        testTitle(page, "Log in");
        HtmlPage pageAfterLogin = login(page);
        testTitle(pageAfterLogin, "Create a card");
        assertEquals(url, page.getWebResponse().getRequestSettings().getUrl().toString());
    }

    @Test
    public void testPostRequest() throws Exception {
        WebClient wc = startWebClient();
        WebRequestSettings request =
            new WebRequestSettings(new URL(url("/cards/CreateCard.action")), HttpMethod.POST);
        HtmlPage page = wc.getPage(request);
        testTitle(page, "Log in");
        HtmlPage pageAfterLogin = login(page);
        testTitle(pageAfterLogin, "Cards");
    }

    @Test
    public void testAjaxRequest() throws Exception {
        WebClient wc = startWebClient();
        WebRequestSettings request =
            new WebRequestSettings(new URL(url("/cards/CreateCard.action")), HttpMethod.GET);
        request.setAdditionalHeader("X-Requested-With", "XMLHttpRequest");
        wc.setThrowExceptionOnFailingStatusCode(false);
        HtmlPage page = wc.getPage(request);
        assertEquals(403, page.getWebResponse().getStatusCode());
    }

    private HtmlPage login(HtmlPage loginPage) throws IOException {
        HtmlForm loginForm = loginPage.getHtmlElementById("loginForm");
        loginForm.getInputByName("userId").type("test");
        loginForm.getInputByName("masterPassword").type("test");
        HtmlPage result = loginForm.getInputByValue("Log in").click();
        return result;
    }
}
