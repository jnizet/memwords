package com.googlecode.memwords.web.integration.loginhistory;

import static com.googlecode.memwords.web.integration.util.IntegrationUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

/**
 * Tests for the Login history page
 * @author JB
 */
public class LoginHistoryTest {
    @Before
    public void setUp() throws Exception {
        setUpData();
    }

    @Test
    public void testPage() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/loginhistory/LoginHistory.action"));
        testBasics(page);
        testTitle(page, "Login history");
    }

    @Test
    public void testTable() throws Exception {
        WebClient wc = startWebClient();
        login(wc);
        HtmlPage page = wc.getPage(url("/loginhistory/LoginHistory.action"));
        HtmlTable table = page.getHtmlElementById("loginHistoryTable");
        // first login: only one data row in the table
        assertEquals(2, table.getRowCount());
        assertTrue(table.getRow(1).getCell(0).asText().startsWith("Current session"));

        wc = startWebClient();
        login(wc);
        page = wc.getPage(url("/loginhistory/LoginHistory.action"));
        table = page.getHtmlElementById("loginHistoryTable");
        // second login: two data rows in the table
        assertEquals(3, table.getRowCount());
        assertTrue(table.getRow(1).getCell(0).asText().startsWith("Current session"));
    }
}
