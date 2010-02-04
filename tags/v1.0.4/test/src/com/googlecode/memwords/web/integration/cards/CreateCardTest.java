package com.googlecode.memwords.web.integration.cards;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Integration tests for the Create Card page
 * @author JB
 */
public class CreateCardTest extends EditCardTestBase {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));
        testBasics(page);
        testTitle(page, "Create a card");
    }

    @Test
    public void testIconUrlLoading() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));

        HtmlForm form = page.getHtmlElementById("createCardForm");
        assertEquals("", form.getInputByName("iconUrl").getValueAttribute());
        assertEquals("/img/card.png", getDisplayedCardIconSrc(page));

        form.getInputByName("url").type("foo");
        form.getInputByName("url").blur();
        assertEquals("", form.getInputByName("iconUrl").getValueAttribute());
        assertEquals("/img/card.png", getDisplayedCardIconSrc(page));

        ((HtmlTextInput) form.getInputByName("url")).select();
        form.getInputByName("url").type("http://www.google.com");
        form.getInputByName("url").blur();
        assertTrue(form.getInputByName("iconUrl").getValueAttribute().contains("google"));
        assertTrue(getDisplayedCardIconSrc(page).contains("google"));

        ((HtmlTextInput) form.getInputByName("url")).select();
        form.getInputByName("url").type("http://www.googlesjdkfhksjdfhksjdhfkjh.com");
        form.getInputByName("url").blur();
        assertEquals("/img/card.png", getDisplayedCardIconSrc(page));
        testErrorExists(page, "An error occurred while getting the icon of the web site. The default icon will be used.");
    }

    @Test
    public void testValidation() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));

        testValidation(page);
    }

    @Test
    public void testValidationWhenComingFromCards() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        getFirstLinkByText(page.getHtmlElementById("cardDetails"), "Create a new card").click();

        testValidation(page);
    }

    @Test
    public void testCancel() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));

        HtmlForm form = page.getHtmlElementById("createCardForm");
        form.getInputByValue("Cancel").click();
        testElementNotPresent(page, "createCardForm");
        WebAssert.assertTextPresent(page, "Create a new card");
        assertEquals(3, page.getHtmlElementById("cards")
                            .getElementsByAttribute("div", "class", "card").size());
    }

    @Test
    public void testFormSubmission() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));

        HtmlForm form = page.getHtmlElementById("createCardForm");
        form.getInputByName("name").type("card4");
        form.getInputByName("login").type("login");
        form.getInputByName("password").type("password");
        form.getInputByValue("Create card").click();

        testMessageExists(page, "The card \"card4\" has been created");
        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");
        assertEquals(4, cardDivs.size());
    }

    @Test
    public void testValidationWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));

        HtmlForm form = page.getHtmlElementById("createCardForm");
        page = form.getInputByValue("Create card").click();
        testTitle(page, "Create a card");
        testErrorExists(page, "Name of the card is a required field");
        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");
        assertEquals(3, cardDivs.size());
    }

    @Test
    public void testCancelWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));

        HtmlForm form = page.getHtmlElementById("createCardForm");
        HtmlPage cardsPage = form.getInputByValue("Cancel").click();
        testTitle(cardsPage, "Cards");
    }

    @Test
    public void testFormSubmissionWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/CreateCard.action"));

        HtmlForm form = page.getHtmlElementById("createCardForm");
        form.getInputByName("name").type("card4");
        form.getInputByName("login").type("login");
        form.getInputByName("password").type("password");
        form.getInputByName("url").type("http://www.google.com");
        HtmlPage cardsPage = form.getInputByValue("Create card").click();

        testTitle(cardsPage, "Cards");
        testMessageExists(cardsPage, "The card \"card4\" has been created.");
        HtmlDivision cardsDiv = cardsPage.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");
        assertEquals(4, cardDivs.size());
        HtmlAnchor card4DetailsLink = getFirstLinkByText(cardsDiv, "card4");
        // test that the icon in the link has been fetched
        assertTrue(card4DetailsLink.getElementsByTagName("img").get(0).asXml().contains("google"));
    }

    private void testValidation(HtmlPage page) throws IOException {
        HtmlForm form = page.getHtmlElementById("createCardForm");
        form.getInputByValue("Create card").click();

        testErrorExists(page, "Name of the card is a required field");
        testErrorExists(page, "Login is a required field");
        testErrorExists(page, "Password is a required field");

        form = page.getHtmlElementById("createCardForm");
        form.getInputByName("name").type("card1");
        form.getInputByValue("Create card").click();

        testErrorExists(page, "You already have a card with the name \"card1\".");
    }
}
