package com.googlecode.memwords.test.util;

import javax.persistence.EntityManager;

import com.googlecode.memwords.guice.MainModule;

/**
 * Base class for tests that need a Google App Engine environment to execute
 * in.
 * @author JB
 */
public class GAETestCase {
    public void setUp() throws Exception {
        TestEnvironment.install();
    }

    public void tearDown() throws Exception {
        TestEnvironment.uninstall();
    }

    protected EntityManager createEntityManager() {
        EntityManager em = MainModule.EMF_INSTANCE.createEntityManager();
        return em;
    }
}
