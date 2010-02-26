package com.googlecode.memwords.web.integration.tools;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static org.junit.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Tests for the tools page
 * @author JB
 */
public class ToolsTest {

    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/tools/Tools.action"));
        testTitle(page, "Tools");
        testBasics(page);
    }

    @Test
    public void testNoScript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        wc.setCssEnabled(true);
        HtmlPage page = wc.getPage(url("/tools/Tools.action"));
        assertTrue(page.asText().contains("The tools on this page are only usable with JavaScript enabled"));
        assertFalse(page.asText().contains("Type a password in the field below"));

        wc.setJavaScriptEnabled(true);
        page = wc.getPage(url("/tools/Tools.action"));
        assertFalse(page.asText().contains("The tools on this page are only usable with JavaScript enabled"));
        assertTrue(page.asText().contains("Type a password in the field below"));
    }

    @Test
    public void testEvalPassword() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/tools/Tools.action"));
        wc.waitForBackgroundJavaScript(10000L);
        HtmlForm form = page.getHtmlElementById("evalPasswordStrengthForm");
        assertFalse(form.asText().contains("10 %"));
        assertTrue(form.asText().contains("0 %"));
        page.getHtmlElementById("passwordToEval").type("he");
        wc.waitForBackgroundJavaScript(10000L);
        assertTrue(form.asText().contains("10 %"));
    }

    @Test
    public void testGeneratePassword() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/tools/Tools.action"));
        wc.waitForBackgroundJavaScript(10000L);
        assertFalse(page.asText().contains("Generated password:"));
        page.getHtmlElementById("generatePasswordButton").click();
        wc.waitForBackgroundJavaScript(10000L);
        assertTrue(page.asText().contains("Generated password:"));
        assertTrue(page.getHtmlElementById("generatedPasswordStrength").asText().contains("93 %"));
    }

    @Test
    public void testGeneratePasswordButtonEnabledHandler() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/tools/Tools.action"));
        wc.waitForBackgroundJavaScript(10000L);
        assertTrue(StringUtils.isEmpty(page.getHtmlElementById("generatePasswordButton").getAttribute("disabled")));
        ((HtmlCheckBoxInput) page.getHtmlElementById("lowerCaseLettersIncluded")).setChecked(false);
        ((HtmlCheckBoxInput) page.getHtmlElementById("upperCaseLettersIncluded")).setChecked(false);
        ((HtmlCheckBoxInput) page.getHtmlElementById("digitsIncluded")).setChecked(false);
        ((HtmlCheckBoxInput) page.getHtmlElementById("specialCharactersIncluded")).setChecked(false);
        assertEquals("disabled", page.getHtmlElementById("generatePasswordButton").getAttribute("disabled"));
    }

    @Test
    public void testMaskAndUnmask() throws Exception {
        WebClient wc = startWebClient();
        HtmlPage page = wc.getPage(url("/tools/Tools.action"));
        wc.waitForBackgroundJavaScript(10000L);
        page.getHtmlElementById("generatePasswordButton").click();
        wc.waitForBackgroundJavaScript(10000L);
        assertEquals("masked", page.getHtmlElementById("generatedPassword").getAttribute("class"));
        assertFalse(page.asText().contains("Mask"));
        page.getAnchorByText("Unmask").click();
        assertEquals("unmasked", page.getHtmlElementById("generatedPassword").getAttribute("class"));
        assertFalse(page.asText().contains("Unmask"));
        page.getAnchorByText("Mask").click();
        assertEquals("masked", page.getHtmlElementById("generatedPassword").getAttribute("class"));
    }
}
