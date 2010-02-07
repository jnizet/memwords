package com.googlecode.memwords.web.integration.cards;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the Card Details page
 * @author JB
 */
public class CardDetailsTest {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToDetailsPage(wc);
        testBasics(page);
        testTitle(page, "Cards");

        wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        page = goToDetailsPage(wc);
        testTitle(page, "Card details");
        testBasics(page);
        testTitle(page, "Card details");
    }

    @Test
    public void testDetailsSection() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToDetailsPage(wc);
        HtmlDivision detailsDiv = page.getHtmlElementById("cardDetails");
        String text = detailsDiv.asText();
        assertTrue(text.contains("card1"));
        assertTrue(text.contains("login1"));
        assertTrue(text.contains("password1"));
        assertTrue(text.contains("http://www.google.com"));
        assertTrue(text.contains("This is the note\r\nfor card1"));
    }

    @Test
    public void testDetailsLinks() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToDetailsPage(wc);
        HtmlDivision detailsDiv = page.getHtmlElementById("cardDetails");

        HtmlAnchor urlLink = getFirstLinkByText(detailsDiv, "http://www.google.com");
        HtmlPage urlPage = urlLink.click();
        assertTrue(urlPage.asXml().contains("Google"));

        // test that the Javascript doesn't blow up
        HtmlAnchor unmaskLink = getFirstLinkByText(detailsDiv, "Unmask");
        unmaskLink.click();
        HtmlAnchor maskLink = getFirstLinkByText(detailsDiv, "Mask");
        maskLink.click();

        HtmlAnchor createLink = getFirstLinkByText(detailsDiv, "Create a new card");
        createLink.click();
        assertNotNull(page.getHtmlElementById("createCardForm"));

        // the details div content has been replaced by the create card form
        getFirstLinkByText(page.getHtmlElementById("cards"), "card1").click();
        HtmlAnchor modifyLink = getFirstLinkByTitle(detailsDiv, "Modify this card");
        modifyLink.click();
        assertNotNull(page.getHtmlElementById("modifyCardForm"));

        getFirstLinkByText(page.getHtmlElementById("cards"), "card1").click();
        HtmlAnchor deleteLink = getFirstLinkByTitle(detailsDiv, "Delete this card");
        deleteLink.click();
        assertNotNull(page.getHtmlElementById("deleteCardForm"));
    }

    @Test
    public void testClose() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToDetailsPage(wc);
        HtmlForm form = page.getHtmlElementById("cardDetailsForm");
        form.getInputByValue("Close").click();
        testElementNotPresent(page, "cardDetailsForm");
        WebAssert.assertTextPresent(page, "Create a new card");
    }

    @Test
    public void testDetailsLinksWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToDetailsPage(wc);
        HtmlDivision detailsDiv = page.getHtmlElementById("cardDetails");

        HtmlAnchor createLink = getFirstLinkByText(detailsDiv, "Create a new card");
        HtmlPage createPage = createLink.click();
        testTitle(createPage, "Create a card");

        HtmlAnchor modifyLink = getFirstLinkByTitle(detailsDiv, "Modify this card");
        HtmlPage modifyPage = modifyLink.click();
        testTitle(modifyPage, "Modify a card");

        HtmlAnchor deleteLink = getFirstLinkByTitle(detailsDiv, "Delete this card");
        HtmlPage deletePage = deleteLink.click();
        testTitle(deletePage, "Delete a card");
    }

    @Test
    public void testCloseWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToDetailsPage(wc);
        HtmlForm form = page.getHtmlElementById("cardDetailsForm");
        page = form.getInputByValue("Close").click();
        testTitle(page, "Cards");
    }

    private HtmlPage goToDetailsPage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        HtmlAnchor detailsLink = getFirstLinkByText(page.getHtmlElementById("cards"), "card1");
        return detailsLink.click();
    }
}
