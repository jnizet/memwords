package com.googlecode.memwords.web.integration.cards;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the Deleta Card page
 * @author JB
 */
public class DeleteCardTest {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToDeletePage(wc);
        testBasics(page);
        testTitle(page, "Cards");

        wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        page = goToDeletePage(wc);
        testBasics(page);
        testTitle(page, "Delete a card");
    }

    @Test
    public void testCancel() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToDeletePage(wc);
        HtmlForm form = page.getHtmlElementById("deleteCardForm");
        form.getInputByValue("No, Cancel").click();
        WebAssert.assertElementNotPresent(page, "deleteCardForm");
        WebAssert.assertTextPresent(page, "Create a new card");
        assertEquals(3, page.getHtmlElementById("cards")
                            .getElementsByAttribute("div", "class", "card").size());
    }

    @Test
    public void testFormSubmission() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToDeletePage(wc);
        HtmlForm form = page.getHtmlElementById("deleteCardForm");
        form.getInputByValue("Yes, Delete").click();
        testMessageExists(page, "The card has been deleted");
        WebAssert.assertElementNotPresent(page, "deleteCardForm");
        WebAssert.assertTextPresent(page, "Create a new card");
        assertEquals(2, page.getHtmlElementById("cards")
                            .getElementsByAttribute("div", "class", "card").size());
        WebAssert.assertTextNotPresent(page, "card1");
    }

    @Test
    public void testCancelWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToDeletePage(wc);
        HtmlForm form = page.getHtmlElementById("deleteCardForm");
        page = form.getInputByValue("No, Cancel").click();
        testTitle(page, "Cards");
        WebAssert.assertElementNotPresent(page, "deleteCardForm");
        WebAssert.assertTextPresent(page, "Create a new card");
        assertEquals(3, page.getHtmlElementById("cards")
                            .getElementsByAttribute("div", "class", "card").size());
    }

    @Test
    public void testFormSubmissionWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToDeletePage(wc);
        HtmlForm form = page.getHtmlElementById("deleteCardForm");
        page = form.getInputByValue("Yes, Delete").click();
        testTitle(page, "Cards");
        testMessageExists(page, "The card has been deleted");
        WebAssert.assertElementNotPresent(page, "deleteCardForm");
        WebAssert.assertTextPresent(page, "Create a new card");
        assertEquals(2, page.getHtmlElementById("cards")
                            .getElementsByAttribute("div", "class", "card").size());
        WebAssert.assertTextNotPresent(page, "card1");
    }

    private HtmlPage goToDeletePage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        HtmlAnchor deleteLink = getFirstLinkByTitle(page.getHtmlElementById("cards"), "Delete card");
        return deleteLink.click();
    }
}
