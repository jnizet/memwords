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

    public static final EntityManagerFactory EMF_INSTANCE =
        Persistence.createEntityManagerFactory("transactions-optional");

    @Override
    public void configure(Binder binder) {
        // Does nothing (everything is done via annotations)
    }

    @Provides
    protected URLFetchService provideURLFetchService() {
        return URLFetchServiceFactory.getURLFetchService();
    }

    @Provides
    @RequestScoped
    protected EntityManager provideEntityManager() {
        return EMF_INSTANCE.createEntityManager();
    }
}
