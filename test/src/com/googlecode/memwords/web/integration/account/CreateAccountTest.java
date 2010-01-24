package com.googlecode.memwords.web.integration.account;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Integration test for the Create Account page
 * @author JB
 */
public class CreateAccountTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/account/CreateAccount.action"));
        testBasics(page);
    }

    @Test
    public void testUserIdAvailability() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/account/CreateAccount.action"));
        HtmlForm form = page.getHtmlElementById("createAccountForm");
        form.getInputByName("userId").type("test");
        form.getInputByName("userId").blur();
        assertEquals("This user ID is not available",
                     page.getHtmlElementById("userIdAvailability").asText());
        form.getInputByName("userId").type("test2");
        form.getInputByName("userId").blur();
        assertEquals("This user ID is available",
                     page.getHtmlElementById("userIdAvailability").asText());
        form.<HtmlTextInput>getInputByName("userId").select();
        form.getInputByName("userId").type(" ");
        form.getInputByName("userId").blur();
        assertEquals("",
                     page.getHtmlElementById("userIdAvailability").asText());
    }

    @Test
    public void testValidation() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/account/CreateAccount.action"));
        HtmlForm form = page.getHtmlElementById("createAccountForm");
        page = form.getInputByValue("Create account").click();
        testTitle(page, "Create an account");
        testErrorExists(page, "Master password is a required field");
        testErrorExists(page, "Master password confirmation is a required field");
        testErrorExists(page, "User ID is a required field");

        form = page.getHtmlElementById("createAccountForm");
        form.getInputByName("masterPassword").type("a");
        form.getInputByName("masterPassword2").type("b");
        page = form.getInputByValue("Create account").click();
        testTitle(page, "Create an account");
        testErrorExists(page, "Master password must be at least 4 characters long");
        testErrorExists(page, "The master password confirmation must be identical to the master password");

        form.getInputByName("userId").type("test");
        form.getInputByName("masterPassword").type("password");
        form.getInputByName("masterPassword2").type("password");
        page = form.getInputByValue("Create account").click();
        testTitle(page, "Create an account");
        testErrorExists(page, "The user ID \"test\" is not available");
    }

    @Test
    public void testFormSubmission() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/account/CreateAccount.action"));
        HtmlForm form = page.getHtmlElementById("createAccountForm");
        form.getInputByName("userId").type("test2");
        form.getInputByName("masterPassword").type("password2");
        form.getInputByName("masterPassword2").type("password2");
        page = form.getInputByValue("Create account").click();
        testTitle(page, "Cards");
    }
}
