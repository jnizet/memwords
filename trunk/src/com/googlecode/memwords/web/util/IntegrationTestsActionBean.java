package com.googlecode.memwords.web.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
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

    /**
     * The entity manager
     */
    private EntityManager em;

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * The card service
     */
    private CardService cardService;

    /**
     * Constructor
     * @param em the entity manager
     * @param accountService the account service
     * @param cardService the card service
     */
    @Inject
    public IntegrationTestsActionBean(EntityManager em,
                                      AccountService accountService,
                                      CardService cardService) {
        this.em = em;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    /**
     * Interceptor method which checks that all requests to this action bean are
     * done in a development environment, and returns an error resolution
     * if it's not the case
     * @return an error resolution if needed, <code>null</code> otherwise
     */
    @Before(stages = LifecycleStage.BindingAndValidation)
    public Resolution checkRunningInDevelopmentEnvironment() {
        if (SystemProperty.environment.value() != SystemProperty.Environment.Value.Development) {
            return new ErrorResolution(HttpServletResponse.SC_FORBIDDEN,
                                       "this action should not be called in production");
        }
        return null;
    }

    /**
     * Dumps the cobertura data to the response, as a serialized object.
     * It uses reflection so that the method compiles even if cobertura is not in the classpath
     * @return <code>null</code>, because this event handler writes everything needed to the response
     * @throws InstantiationException
     */
    @DefaultHandler
    public Resolution flushCobertura() throws ClassNotFoundException,
                                              SecurityException,
                                              NoSuchMethodException,
                                              IllegalArgumentException,
                                              IllegalAccessException,
                                              InvocationTargetException,
                                              IOException,
                                              InstantiationException {
        String projectDataClassName = "net.sourceforge.cobertura.coveragedata.ProjectData";
        Class<?> projectDataClass = Class.forName(projectDataClassName);
        Object projectData = projectDataClass.newInstance();

        String touchCollectorClassName = "net.sourceforge.cobertura.coveragedata.TouchCollector";
        Class<?> touchCollectorClass = Class.forName(touchCollectorClassName);

        String methodName = "applyTouchesOnProjectData";
        java.lang.reflect.Method applyTouchesOnProjectDataMethod =
            touchCollectorClass.getDeclaredMethod(methodName, new Class[] {projectDataClass});
        applyTouchesOnProjectDataMethod.invoke(null, projectData);

        getContext().getResponse().setContentType("application/octet-stream");
        OutputStream out = getContext().getResponse().getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        try {
            oos.writeObject(projectData);
        }
        finally {
            oos.close();
        }

        return null;
    }

    /**
     * Sets up the data necessary for integration tests : empties the database, then
     * repopulates it with test data
     * @return <code>null</code>, because this event handler writes everything needed to the response
     */
    @SuppressWarnings("unchecked")
    public Resolution setUp() throws IOException {
        Query query = em.createQuery("select account from Account account");
        List<Account> accounts = query.getResultList();
        for (Account account : accounts) {
            accountService.destroyAccount(account.getUserId());
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
