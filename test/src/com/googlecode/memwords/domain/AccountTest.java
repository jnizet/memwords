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

    @Test
    public void testAddHistoricLogin() {
        Account account = new Account();
        assertTrue(account.getHistoricLogins().isEmpty());
        HistoricLogin h1 = new HistoricLogin();
        boolean mustRemove = account.addHistoricLogin(h1);
        assertFalse(mustRemove);
        HistoricLogin h2 = new HistoricLogin();
        mustRemove = account.addHistoricLogin(h2);
        assertFalse(mustRemove);
        assertSame(h2, account.getHistoricLogins().get(0));
        assertSame(h1, account.getHistoricLogins().get(1));
        while (account.getHistoricLogins().size() < Account.MAX_HISTORIC_LOGIN_COUNT) {
            mustRemove = account.addHistoricLogin(new HistoricLogin());
            assertFalse(mustRemove);
        }
        mustRemove = account.addHistoricLogin(new HistoricLogin());
        assertTrue(mustRemove);
    }
}
