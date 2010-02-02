package com.googlecode.memwords.facade.loginhistory;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.HistoricLogin;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.facade.account.AccountServiceImpl;
import com.googlecode.memwords.facade.util.CryptoEngineImpl;
import com.googlecode.memwords.test.util.GAETestCase;

/**
 * Tests for the class {@link LoginHistoryServiceImpl}
 * @author JB
 */
public class LoginHistoryServiceImplTest extends GAETestCase {

    private EntityManager em;
    private LoginHistoryServiceImpl impl;
    private AccountService accountService;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        em = createEntityManager();
        impl = new LoginHistoryServiceImpl(em);
        accountService = new AccountServiceImpl(em, new CryptoEngineImpl());
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testAddLogin() throws Exception {
        String userId = "userId";
        accountService.createAccount(userId, "masterPassword");
        impl.addLogin(userId, "userAgent", "ip");
        List<HistoricLogin> history = impl.getLoginHistory(userId);
        assertEquals(1, history.size());
        assertNull(impl.getLatestHistoricLogin(userId));
        assertEquals("userAgent", history.get(0).getUserAgent());
        assertEquals("ip", history.get(0).getIp());
        assertTrue(System.currentTimeMillis() - history.get(0).getDate().getTime() < 10000L);

        // without this sleep, two historic logins could have the same date, and the
        // order would be random
        Thread.sleep(100L);

        impl.addLogin(userId, "userAgent2", "ip2");
        history = impl.getLoginHistory(userId);
        assertEquals(2, history.size());
        assertEquals("userAgent2", history.get(0).getUserAgent());
        assertEquals("userAgent", impl.getLatestHistoricLogin(userId).getUserAgent());

        for (int i = 0; i < Account.MAX_HISTORIC_LOGIN_COUNT; i++) {
            impl.addLogin(userId, "userAgent", "ip");
        }
    }
}
