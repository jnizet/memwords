package com.googlecode.memwords.web.integration.preferences;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Integration tests for the preferences page
 * @author JB
 */
public class PreferencesTest {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/Preferences.action"));
        testBasics(page);
        testTitle(page, "Preferences");
    }

    @Test
    public void testLinks() throws Exception {
        WebClient wc = startWebClient(false);
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/Preferences.action"));
        HtmlAnchor changePreferredLocaleLink = page.getAnchorByText("Choose your preferred language");
        changePreferredLocaleLink.click();
        wc.waitForBackgroundJavaScript(10000L);
        WebAssert.assertElementPresent(page, "changePreferredLocaleForm");
        testBasics(page);

        HtmlAnchor changePreferredTimeZoneLink = page.getAnchorByText("Choose your time zone");
        changePreferredTimeZoneLink.click();
        wc.waitForBackgroundJavaScript(10000L);
        WebAssert.assertElementNotPresent(page, "changePreferredLocaleForm");
        WebAssert.assertElementPresent(page, "changePreferredTimeZoneForm");
        testBasics(page);

        HtmlAnchor changePasswordPreferencesLink = page.getAnchorByText("Password preferences");
        changePasswordPreferencesLink.click();
        wc.waitForBackgroundJavaScript(10000L);
        WebAssert.assertElementNotPresent(page, "changePreferredTimeZoneForm");
        WebAssert.assertElementPresent(page, "changePasswordPreferencesForm");
        testBasics(page);
    }

    @Test
    public void testLinksWithoutJavascript() throws Exception {
        WebClient wc = startWebClient();
        wc.setJavaScriptEnabled(false);
        login(wc);
        HtmlPage page = wc.getPage(url("/preferences/Preferences.action"));
        HtmlAnchor changePreferredLocaleLink = page.getAnchorByText("Choose your preferred language");
        HtmlPage changePreferredLocalePage = changePreferredLocaleLink.click();
        testTitle(changePreferredLocalePage, "Choose preferred language");

        HtmlAnchor changePreferredTimeZoneLink = page.getAnchorByText("Choose your time zone");
        HtmlPage changePreferredTimeZonePage = changePreferredTimeZoneLink.click();
        testTitle(changePreferredTimeZonePage, "Choose time zone");

        HtmlAnchor changePasswordPreferencesLink = page.getAnchorByText("Password preferences");
        HtmlPage changePasswordPreferencesPage = changePasswordPreferencesLink.click();
        testTitle(changePasswordPreferencesPage, "Password preferences");
    }
}
