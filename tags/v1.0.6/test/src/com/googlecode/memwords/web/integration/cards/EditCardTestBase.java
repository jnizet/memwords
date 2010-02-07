package com.googlecode.memwords.web.integration.cards;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static junit.framework.Assert.*;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class EditCardTestBase {
    protected String getDisplayedCardIconSrc(HtmlPage page) throws Exception {
        HtmlSpan defaultSpan = page.getHtmlElementById("defaultIconUrlSpan");
        HtmlSpan span = page.getHtmlElementById("iconUrlSpan");
        if (defaultSpan.isDisplayed()) {
            return getImageSrcFromSpan(defaultSpan);
        }
        else if (span.isDisplayed()) {
            return getImageSrcFromSpan(span);
        }
        else {
            return null;
        }
    }

    private String getImageSrcFromSpan(HtmlSpan span) {
        return span.getHtmlElementsByTagName("img").get(0).getAttribute("src");
    }

    protected void testPasswordGeneration(HtmlPage page, String formId) throws IOException {
        HtmlForm form = page.getHtmlElementById(formId);
        form.getInputByName("password").setValueAttribute("");
        HtmlAnchor generatePasswordLink = getFirstLinkByText(form, "Generate randomly");
        generatePasswordLink.click();
        assertEquals(8, form.getInputByName("password").getValueAttribute().length());
    }

    protected void testPasswordGenerationOptionsForm(HtmlPage page, String formId) throws IOException {
        WebClient wc = page.getWebClient();
        HtmlForm form = page.getHtmlElementById(formId);
        HtmlAnchor generatePasswordWithOptionsLink = getFirstLinkByText(form, "with options");
        generatePasswordWithOptionsLink.click();
        wc.waitForBackgroundJavaScript(10000L);
        testBasics(page);

        assertEquals("8", page.getHtmlElementById("passwordLength").asText());
        assertTrue(((HtmlCheckBoxInput) page.getHtmlElementById("lowerCaseLettersIncluded")).isChecked());
        assertTrue(((HtmlCheckBoxInput) page.getHtmlElementById("upperCaseLettersIncluded")).isChecked());
        assertTrue(((HtmlCheckBoxInput) page.getHtmlElementById("digitsIncluded")).isChecked());
        assertTrue(((HtmlCheckBoxInput) page.getHtmlElementById("specialCharactersIncluded")).isChecked());
        assertTrue(StringUtils.isEmpty(page.getHtmlElementById("generatePasswordButton").getAttribute("disabled")));

        ((HtmlCheckBoxInput) page.getHtmlElementById("lowerCaseLettersIncluded")).setChecked(false);
        ((HtmlCheckBoxInput) page.getHtmlElementById("upperCaseLettersIncluded")).setChecked(false);
        ((HtmlCheckBoxInput) page.getHtmlElementById("digitsIncluded")).setChecked(false);
        ((HtmlCheckBoxInput) page.getHtmlElementById("specialCharactersIncluded")).setChecked(false);
        assertEquals("disabled", page.getHtmlElementById("generatePasswordButton").getAttribute("disabled"));
        ((HtmlCheckBoxInput) page.getHtmlElementById("lowerCaseLettersIncluded")).setChecked(true);
        assertTrue(StringUtils.isEmpty(page.getHtmlElementById("generatePasswordButton").getAttribute("disabled")));
    }

    protected void testPasswordGenerationCancel(HtmlPage page, String formId) throws IOException {
        WebClient wc = page.getWebClient();
        HtmlForm form = page.getHtmlElementById(formId);
        HtmlAnchor generatePasswordWithOptionsLink = getFirstLinkByText(form, "with options");
        generatePasswordWithOptionsLink.click();
        wc.waitForBackgroundJavaScript(10000L);
        assertFalse(StringUtils.isEmpty(page.getHtmlElementById("generatePasswordDiv").asText()));
        page.getHtmlElementById("cancelPasswordGenerationButton").click();
        wc.waitForBackgroundJavaScript(10000L);
        assertTrue(StringUtils.isEmpty(page.getHtmlElementById("generatePasswordDiv").asText()));
    }

    protected void testPasswordGenerationSubmit(HtmlPage page, String formId) throws IOException {
        WebClient wc = page.getWebClient();
        HtmlForm form = page.getHtmlElementById(formId);
        form.getInputByName("password").setValueAttribute("");
        HtmlAnchor generatePasswordWithOptionsLink = getFirstLinkByText(form, "with options");
        generatePasswordWithOptionsLink.click();
        wc.waitForBackgroundJavaScript(10000L);
        page.getHtmlElementById("generatePasswordButton").click();
        wc.waitForBackgroundJavaScript(10000L);
        assertTrue(StringUtils.isEmpty(page.getHtmlElementById("generatePasswordDiv").asText()));
        assertEquals(8, form.getInputByName("password").getValueAttribute().length());
    }
}
