package com.googlecode.memwords.web.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the class {@link HtmlUtils}
 * @author JB
 *
 */
public class HtmlUtilsTest {
    @Test
    public void testNlToBr() {
        assertEquals("", HtmlUtils.nlToBr(null, false));
        assertEquals("hello", HtmlUtils.nlToBr("hello", false));
        assertEquals("hello<br/>world", HtmlUtils.nlToBr("hello\nworld", false));
        assertEquals("hello<br/>world", HtmlUtils.nlToBr("hello\rworld", false));
        assertEquals("hello<br/>world", HtmlUtils.nlToBr("hello\r\nworld", false));
        assertEquals("hello<br/><br/><br/>world", HtmlUtils.nlToBr("hello\n\n\nworld", false));

        assertEquals("", HtmlUtils.nlToBr(null, true));
        assertEquals("hel&lt;&gt;lo", HtmlUtils.nlToBr("hel<>lo", false));
        assertEquals("hel&lt;&gt;lo<br/>wor&lt;&gt;ld", HtmlUtils.nlToBr("hel<>lo\nwor<>ld", false));
        assertEquals("hel&lt;&gt;lo<br/>wor&lt;&gt;ld", HtmlUtils.nlToBr("hel<>lo\rwor<>ld", false));
        assertEquals("hel&lt;&gt;lo<br/>wor&lt;&gt;ld", HtmlUtils.nlToBr("hel<>lo\r\nwor<>ld", false));
        assertEquals("hel&lt;&gt;lo<br/><br/><br/>wor&lt;&gt;ld", HtmlUtils.nlToBr("hel<>lo\n\n\nwor<>ld", false));
    }
}
