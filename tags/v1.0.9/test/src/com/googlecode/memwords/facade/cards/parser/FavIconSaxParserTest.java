package com.googlecode.memwords.facade.cards.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

public class FavIconSaxParserTest {

    private FavIconSaxParser parser;

    @Before
    public void setUp() {
        parser = new FavIconSaxParser();
    }

    @Test
    public void testFindFavIcon() throws Exception {
        String html1 = "<html><head><title>Title</title>"
                       + "<link rel='stylesheet' href='/a.css'/>"
                       + "</head><body></body></html>";
        String html2 = "<html><head><title>Title</title>"
            + "<link rel='stylesheet' href='/a.css'/>"
            + "<link rel='shortcut icon' href='/favicon.png'/>"
            + "</head><body></body></html>";
        assertNull(parser.findFavIcon(new InputSource(new StringReader(html1))));
        assertEquals("/favicon.png", parser.findFavIcon(new InputSource(new StringReader(html2))));
    }

}
