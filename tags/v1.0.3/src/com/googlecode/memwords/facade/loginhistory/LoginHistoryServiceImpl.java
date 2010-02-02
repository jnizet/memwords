package com.googlecode.memwords.facade.loginhistory;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.HistoricLogin;

/**
 * Implementation of {@link LoginHistoryService}
 * @author JB
 */
@Singleton
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private EntityManager em;

    @Inject
    public LoginHistoryServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addLogin(String userId, String userAgent, String ip) {
        // Add and remove in two separate transactions because of a bug in datanucleus
        boolean mustRemove = false;
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Account account = em.find(Account.class, userId);
            HistoricLogin historicLogin = new HistoricLogin(new Date(), userAgent, ip);
            historicLogin.setAccount(account);
            mustRemove = account.addHistoricLogin(historicLogin);
            tx.commit();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

        if (mustRemove) {
            tx = em.getTransaction();
            try {
                tx.begin();
                Account account = em.find(Account.class, userId);
                account.removeOldHistoricLogins();
                tx.commit();
            }
            finally {
                if (tx.isActive()) {
                    tx.rollback();
                }
            }
        }
    }

    @Override
    public List<HistoricLogin> getLoginHistory(String userId) {
        Account account = em.find(Account.class, userId);
        return account.getHistoricLogins();
    }

    @Override
    public HistoricLogin getLatestHistoricLogin(String userId) {
        Account account = em.find(Account.class, userId);
        List<HistoricLogin> logins = account.getHistoricLogins();
        if (logins.size() > 1) {
            return logins.get(1);
        }
        return null;
    }
}
