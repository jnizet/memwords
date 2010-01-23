package com.googlecode.memwords.web.integration.cards;

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
}
