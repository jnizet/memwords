package com.googlecode.memwords.web.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Inject;
import com.googlecode.memwords.domain.Account;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.web.MwActionBean;

/**
 * Action bean used to help with integration tests and cobertura
 * @author JB
 */
public class IntegrationTestsActionBean extends MwActionBean {

    private EntityManager em;
    private AccountService accountService;
    private CardService cardService;

    @Inject
    public IntegrationTestsActionBean(EntityManager em,
                                      AccountService accountService,
                                      CardService cardService) {
        this.em = em;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @Before(stages = LifecycleStage.BindingAndValidation)
    public Resolution checkRunningInDevelopmentEnvironment() {
        if (SystemProperty.environment.value() != SystemProperty.Environment.Value.Development) {
            return new ErrorResolution(HttpServletResponse.SC_FORBIDDEN,
                                       "this action should not be called in production");
        }
        return null;
    }

    @DefaultHandler
    public Resolution flushCobertura() throws ClassNotFoundException,
                                              SecurityException,
                                              NoSuchMethodException,
                                              IllegalArgumentException,
                                              IllegalAccessException,
                                              InvocationTargetException,
                                              IOException {
        System.out.println("flushing cobertura...");
        String className = "net.sourceforge.cobertura.coveragedata.ProjectData";
        String methodName = "getGlobalProjectData";
        Class<?> projectDataClass = Class.forName(className);
        java.lang.reflect.Method getGlobalProjectDataMethod =
            projectDataClass.getDeclaredMethod(methodName, new Class[0]);
        Object globalProjectData = getGlobalProjectDataMethod.invoke(null, new Object[0]);

        System.out.println("global project data obtained : " + globalProjectData);

        getContext().getResponse().setContentType("application/octet-stream");
        OutputStream out = getContext().getResponse().getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        try {
            oos.writeObject(globalProjectData);
        }
        finally {
            oos.close();
        }

        System.out.println("cobertura flushed");
        return null;
    }

    /**
     * sets up the data necessary for integration tests : empties the database, then
     * repopulates it with test data
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public Resolution setUp() throws IOException {
        Query query = em.createQuery("select account from Account account");
        List<Account> accounts = query.getResultList();
        for (Account account : accounts) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.remove(account);
                tx.commit();
            }
            finally {
                if (tx.isActive()) {
                    tx.rollback();
                }
            }
        }
        String testUserId = "test";
        SecretKey testSecretKey = accountService.createAccount(testUserId, "test").getEncryptionKey();

        CardDetails cardDetails = new CardDetails(null,
                                                  "card1",
                                                  "login1",
                                                  "password1",
                                                  "http://www.google.com",
                                                  "http://www.google.com/favicon.ico",
                                                  "This is the note\nfor card1");
        cardService.createCard(testUserId,
                               cardDetails,
                               testSecretKey);
        cardDetails = new CardDetails(null,
                                      "card2",
                                      "login2",
                                      "password2",
                                      "http://www.yahoo.com",
                                      "http://www.yahoo.com/favicon.ico",
                                      "This is the note\nfor card2");
        cardService.createCard(testUserId,
                               cardDetails,
                               testSecretKey);
        cardDetails = new CardDetails(null,
                                      "card3",
                                      "login3",
                                      "password3",
                                      "http://www.my.site.com",
                                      null,
                                      "This is the note\nfor card3");
        cardService.createCard(testUserId,
                               cardDetails,
                               testSecretKey);

        getContext().getResponse().setContentType("text/plain");
        getContext().getResponse().getWriter().write("OK");
        return null;
    }
}
