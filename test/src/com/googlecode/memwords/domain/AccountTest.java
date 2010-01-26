package com.googlecode.memwords.domain;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

/**
 * Unit tests for the {@link Account} class
 * @author JB
 *
 */
public class AccountTest {
    @Test
    public void testGetLocale() {
        Account account = new Account();
        account.setPreferredLocale(null);
        assertNull(account.getPreferredLocale());
        account.setPreferredLocale(new Locale("fr"));
        assertEquals("fr", account.getPreferredLocale().toString());
        account.setPreferredLocale(new Locale("fr", "FR"));
        assertEquals("fr_FR", account.getPreferredLocale().toString());
    }
}
