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
        HtmlPage page = goToCreatePage(wc);
        testBasics(page);
        testTitle(page, "Cards");

        wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        page = goToCreatePage(wc);
        testBasics(page);
        testTitle(page, "Create a card");
        assertEquals(3, page.getHtmlElementById("cards").getElementsByAttribute("div", "class", "card").size());
    }

    @Test
    public void testIconUrlLoading() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToCreatePage(wc);

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
        HtmlPage page = goToCreatePage(wc);

        testValidation(page);
    }

    @Test
    public void testCancel() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToCreatePage(wc);

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
        HtmlPage page = goToCreatePage(wc);

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
        HtmlPage page = goToCreatePage(wc);

        testValidation(page);
    }

    @Test
    public void testCancelWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToCreatePage(wc);

        HtmlForm form = page.getHtmlElementById("createCardForm");
        HtmlPage cardsPage = form.getInputByValue("Cancel").click();
        testTitle(cardsPage, "Cards");
    }

    @Test
    public void testFormSubmissionWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToCreatePage(wc);

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

    @Test
    public void testPasswordGeneration() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToCreatePage(wc);
        testPasswordGeneration(page, "createCardForm");
    }

    @Test
    public void testPasswordGenerationOptionsForm() throws Exception {
        WebClient wc = startWebClient(true);
        HtmlPage page = goToCreatePage(wc);
        testPasswordGenerationOptionsForm(page, "createCardForm");
    }

    @Test
    public void testPasswordGenerationCancel() throws Exception {
        WebClient wc = startWebClient(true);
        HtmlPage page = goToCreatePage(wc);
        testPasswordGenerationCancel(page, "createCardForm");
    }

    @Test
    public void testPasswordGenerationSubmit() throws Exception {
        WebClient wc = startWebClient(true);
        HtmlPage page = goToCreatePage(wc);
        testPasswordGenerationSubmit(page, "createCardForm");
    }

    private void testValidation(HtmlPage page) throws IOException {
        HtmlForm form = page.getHtmlElementById("createCardForm");
        page = form.getInputByValue("Create card").click();

        testErrorExists(page, "Name of the card is a required field");
        testErrorExists(page, "Login is a required field");
        testErrorExists(page, "Password is a required field");

        form = page.getHtmlElementById("createCardForm");
        form.getInputByName("name").type("card1");
        page = form.getInputByValue("Create card").click();

        testErrorExists(page, "You already have a card with the name \"card1\".");
    }

    private HtmlPage goToCreatePage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        HtmlAnchor createLink = getFirstLinkByText(page.getHtmlElementById("cardDetails"), "Create a new card");
        return createLink.click();
    }
}
