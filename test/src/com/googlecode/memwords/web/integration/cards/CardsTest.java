package com.googlecode.memwords.web.integration.cards;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration test for the Cards page
 * @author JB
 */
public class CardsTest {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        testBasics(page);
        testTitle(page, "Cards");
    }

    @Test
    public void testCardsList() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");
        assertEquals(3, cardDivs.size());

        // test card icon when icon URL is not null
        HtmlDivision card1Div = cardDivs.get(0);
        assertEquals("card1", card1Div.asText());
        HtmlAnchor detailsLink = getFirstLinkByText(card1Div, "card1");
        HtmlImage cardIcon = (HtmlImage) detailsLink.getHtmlElementsByTagName("img").get(0);
        assertEquals("http://www.google.com/favicon.ico", cardIcon.getAttribute("src"));

        // test card icon when icon URL is null
        HtmlDivision card3Div = cardDivs.get(2);
        assertEquals("card3", card3Div.asText());
        detailsLink = getFirstLinkByText(card3Div, "card3");
        cardIcon = (HtmlImage) detailsLink.getHtmlElementsByTagName("img").get(0);
        assertEquals("/img/card.png", cardIcon.getAttribute("src"));
    }

    @Test
    public void testCardLinksWithJavascript() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));

        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");

        HtmlDivision card1Div = cardDivs.get(0);
        HtmlDivision card2Div = cardDivs.get(1);
        HtmlDivision card3Div = cardDivs.get(2);

        HtmlAnchor detailsLink = getFirstLinkByText(card1Div, "card1");

        detailsLink.click();
        testTitle(page, "Cards");
        testBasics(page);
        assertTrue(page.getHtmlElementById("cardDetails").asText().contains("card1"));
        testElementNotPresent(page, "createCardForm");
        testElementNotPresent(page, "modifyCardForm");
        testElementNotPresent(page, "deleteCardForm");

        HtmlAnchor modifyLink = getFirstLinkByTitle(card2Div, "Modify card");
        modifyLink.click();
        assertTrue(page.getHtmlElementById("cardDetails").asText().contains("card2"));
        testElementNotPresent(page, "createCardForm");
        assertNotNull(page.getHtmlElementById("modifyCardForm"));
        testElementNotPresent(page, "deleteCardForm");

        HtmlAnchor deleteLink = getFirstLinkByTitle(card3Div, "Delete card");
        deleteLink.click();
        assertTrue(page.getHtmlElementById("cardDetails").asText().contains("card3"));
        testElementNotPresent(page, "createCardForm");
        testElementNotPresent(page, "modifyCardForm");
        assertNotNull(page.getHtmlElementById("deleteCardForm"));
    }

    @Test
    public void testCardLinksWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));

        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");

        HtmlDivision card1Div = cardDivs.get(0);
        HtmlDivision card2Div = cardDivs.get(1);
        HtmlDivision card3Div = cardDivs.get(2);

        HtmlAnchor detailsLink = getFirstLinkByText(card1Div, "card1");

        HtmlPage detailsPage = detailsLink.click();
        testTitle(detailsPage, "Card Details");

        HtmlAnchor modifyLink = getFirstLinkByTitle(card2Div, "Modify card");
        HtmlPage modifyPage = modifyLink.click();
        testTitle(modifyPage, "Modify a card");

        HtmlAnchor deleteLink = getFirstLinkByTitle(card3Div, "Delete card");
        HtmlPage deletePage = deleteLink.click();
        testTitle(deletePage, "Delete a card");
    }

    @Test
    public void testCreateCardLinkWithJavascript() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        HtmlDivision cardDetailsDiv = page.getHtmlElementById("cardDetails");

        HtmlAnchor createCardLink = getFirstLinkByText(cardDetailsDiv, "Create a new card");
        createCardLink.click();
        assertNotNull(page.getHtmlElementById("createCardForm"));
    }

    @Test
    public void testCreateCardLinkWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        HtmlDivision cardDetailsDiv = page.getHtmlElementById("cardDetails");

        HtmlAnchor createCardLink = getFirstLinkByText(cardDetailsDiv, "Create a new card");
        HtmlPage createCardPage = createCardLink.click();
        testTitle(createCardPage, "Create a card");
        assertNotNull(createCardPage.getHtmlElementById("createCardForm"));
    }
}
