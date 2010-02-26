package com.googlecode.memwords.guice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

/**
 * The main (and only) Guice module used by the application
 * @author JB
 */
public class MainModule implements Module {

    /**
     * The singleton entity manager factory. Should only be used by tests,
     * since the entity manager can be injected.
     */
    public static final EntityManagerFactory EMF_INSTANCE =
        Persistence.createEntityManagerFactory("transactions-optional");

    @Override
    public void configure(Binder binder) {
        // Does nothing (everything is done via annotations)
    }

    /**
     * Allows injecting instances of <code>URLFetchService</code>
     * @return an instance of the URL fetch service
     */
    @Provides
    protected URLFetchService provideURLFetchService() {
        return URLFetchServiceFactory.getURLFetchService();
    }

    /**
     * Allows injecting instances of <code>EntityManager</code>
     * @return an instance of the entity manager, scoped to the request
     */
    @Provides
    @RequestScoped
    protected EntityManager provideEntityManager() {
        return EMF_INSTANCE.createEntityManager();
    }
}
