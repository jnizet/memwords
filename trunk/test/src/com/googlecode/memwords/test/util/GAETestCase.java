package com.googlecode.memwords.test.util;

import javax.persistence.EntityManager;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.memwords.guice.MainModule;

/**
 * Base class for tests that need a Google App Engine environment to execute
 * in.
 * @author JB
 */
public class GAETestCase {

    private LocalServiceTestHelper testHelper;

    public void setUp() throws Exception {
        LocalDatastoreServiceTestConfig datastoreTestConfig = new LocalDatastoreServiceTestConfig();
        datastoreTestConfig.setBackingStoreLocation("test/temp");
        datastoreTestConfig.setNoStorage(true);
        this.testHelper = new LocalServiceTestHelper(datastoreTestConfig);
        testHelper.setUp();
    }

    public void tearDown() throws Exception {
        testHelper.tearDown();
    }

    protected EntityManager createEntityManager() {
        EntityManager em = MainModule.EMF_INSTANCE.createEntityManager();
        return em;
    }
}
