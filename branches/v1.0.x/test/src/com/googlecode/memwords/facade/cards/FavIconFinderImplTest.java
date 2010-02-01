package com.googlecode.memwords.facade.cards;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

public class FavIconFinderImplTest {

    private FavIconFinderImpl impl;

    @Before
    public void setUp() throws Exception {
        impl = new FavIconFinderImpl();
    }

    @Test
    public void testFindFavIcon() throws Exception {
        String html1 = "<html><head><title>Title</title>"
            + "<link rel='stylesheet' href='/a.css'/>"
            + "<link rel='shortcut icon' href='/favicon.png'/>"
            + "</head><body></body></html>";
        String html2 = "<html><head><title>Title</title>"
            + "<link rel='stylesheet' href='/a.css'/>"
            + "<link rel='shortcut icon' href='favicon.png'/>"
            + "</head><body></body></html>";
        String html3 = "<html><head><title>Title</title>"
            + "<link rel='stylesheet' href='/a.css'/>"
            + "</head><body></body></html>";
        URL baseUrl = new URL("http://my.domain.com/foo/index.html");
        assertEquals("http://my.domain.com/favicon.png",
                     impl.findFavIcon(new InputSource(new StringReader(html1)), baseUrl));
        assertEquals("http://my.domain.com/foo/favicon.png",
                     impl.findFavIcon(new InputSource(new StringReader(html2)), baseUrl));
        assertNull(impl.findFavIcon(new InputSource(new StringReader(html3)), baseUrl));

        baseUrl = new URL("http://my.domain.com");
        assertEquals("http://my.domain.com/favicon.png",
                     impl.findFavIcon(new InputSource(new StringReader(html1)), baseUrl));
        assertEquals("http://my.domain.com/favicon.png",
                     impl.findFavIcon(new InputSource(new StringReader(html2)), baseUrl));
        assertNull(impl.findFavIcon(new InputSource(new StringReader(html3)), baseUrl));
    }
}
