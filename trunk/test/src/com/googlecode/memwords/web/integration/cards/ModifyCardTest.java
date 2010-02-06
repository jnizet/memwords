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
 * Integration tests for the Modify Card page
 * @author JB
 */
public class ModifyCardTest extends EditCardTestBase {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToModifyPage(wc);

        testBasics(page);
        testTitle(page, "Cards");

        wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        page = goToModifyPage(wc);
        testBasics(page);
        testTitle(page, "Modify a card");
        assertEquals(3,
                     page.getHtmlElementById("cards").getElementsByAttribute("div", "class", "card").size());
    }

    @Test
    public void testIconUrlLoading() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToModifyPage(wc);

        HtmlForm form = page.getHtmlElementById("modifyCardForm");
        assertTrue(form.getInputByName("iconUrl").getValueAttribute().contains("google"));
        assertTrue(getDisplayedCardIconSrc(page).contains("google"));

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
        HtmlPage page = goToModifyPage(wc);

        testValidation(page);
    }

    @Test
    public void testCancel() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToModifyPage(wc);

        HtmlForm form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("name").setValueAttribute("");
        form.getInputByValue("Cancel").click();
        testElementNotPresent(page, "modifyCardForm");
        WebAssert.assertTextPresent(page, "Create a new card");
        assertEquals(3, page.getHtmlElementById("cards")
                            .getElementsByAttribute("div", "class", "card").size());
    }

    @Test
    public void testFormSubmission() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToModifyPage(wc);

        HtmlForm form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("name").setValueAttribute("card4");
        form.getInputByName("login").setValueAttribute("login4");
        form.getInputByName("password").setValueAttribute("password4");
        form.getInputByValue("Modify card").click();

        testMessageExists(page, "The card \"card4\" has been modified.");
        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");
        assertEquals(3, cardDivs.size());
        assertTrue(cardsDiv.asText().contains("card4"));
        assertFalse(cardsDiv.asText().contains("card1"));
    }

    @Test
    public void testValidationWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToModifyPage(wc);

        HtmlForm form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("name").setValueAttribute("");
        page = form.getInputByValue("Modify card").click();
        testTitle(page, "Modify a card");
        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");
        assertEquals(3, cardDivs.size());

        // test that an error is triggered when checking the change password checkbox is checked and
        // the password is empty
        form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("changePassword").setChecked(true);
        form.getInputByName("password").setValueAttribute("");
        page = form.getInputByValue("Modify card").click();
        testTitle(page, "Modify a card");
        testErrorExists(page, "Password is a required field");

        // test that no error is triggered when not checking the change password checkbox
        // and the password is empty
        form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("changePassword").setChecked(false);
        form.getInputByName("password").setValueAttribute("");
        page = form.getInputByValue("Modify card").click();
        testTitle(page, "Modify a card");
        assertFalse(page.getHtmlElementById("messages").asXml().contains("Password is a required field"));


    }

    @Test
    public void testFormSubmissionWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToModifyPage(wc);

        HtmlForm form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("name").setValueAttribute("card4");
        page = form.getInputByValue("Modify card").click();

        testTitle(page, "Cards");
        testMessageExists(page, "The card \"card4\" has been modified.");
        HtmlDivision cardsDiv = page.getHtmlElementById("cards");
        List<HtmlDivision> cardDivs = cardsDiv.getElementsByAttribute("div", "class", "card");
        assertEquals(3, cardDivs.size());
        assertTrue(cardsDiv.asText().contains("card4"));
        assertFalse(cardsDiv.asText().contains("card1"));
    }

    @Test
    public void testCancelWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        HtmlPage page = goToModifyPage(wc);

        HtmlForm form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("name").setValueAttribute("");
        page = form.getInputByValue("Cancel").click();
        testTitle(page, "Cards");
        testElementNotPresent(page, "modifyCardForm");
        WebAssert.assertTextPresent(page, "Create a new card");
        assertEquals(3, page.getHtmlElementById("cards")
                            .getElementsByAttribute("div", "class", "card").size());
    }

    @Test
    public void testPasswordGeneration() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = goToModifyPage(wc);
        testPasswordGeneration(page, "modifyCardForm");
    }

    @Test
    public void testPasswordGenerationOptionsForm() throws Exception {
        WebClient wc = startWebClient(true);
        HtmlPage page = goToModifyPage(wc);
        testPasswordGenerationOptionsForm(page, "modifyCardForm");
    }

    @Test
    public void testPasswordGenerationCancel() throws Exception {
        WebClient wc = startWebClient(true);
        HtmlPage page = goToModifyPage(wc);
        testPasswordGenerationCancel(page, "modifyCardForm");
    }

    @Test
    public void testPasswordGenerationSubmit() throws Exception {
        WebClient wc = startWebClient(true);
        HtmlPage page = goToModifyPage(wc);
        testPasswordGenerationSubmit(page, "modifyCardForm");
    }

    private HtmlPage goToModifyPage(WebClient wc) throws Exception {
        login(wc);
        HtmlPage page = wc.getPage(url("/cards/Cards.action"));
        HtmlAnchor modifyLink = getFirstLinkByTitle(page.getHtmlElementById("cards"), "Modify card");
        return modifyLink.click();
    }

    private void testValidation(HtmlPage page) throws IOException {
        HtmlForm form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("name").setValueAttribute("");
        form.getInputByName("login").setValueAttribute("");
        form.getInputByName("password").setValueAttribute("");

        form.getInputByValue("Modify card").click();

        testErrorExists(page, "Name of the card is a required field");
        testErrorExists(page, "Login is a required field");
        testErrorExists(page, "Password is a required field");

        form = page.getHtmlElementById("modifyCardForm");
        form.getInputByName("name").type("card2");
        form.getInputByValue("Modify card").click();

        testErrorExists(page, "You already have a card with the name \"card2\".");
    }
}
