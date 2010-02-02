package com.googlecode.memwords.test.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

/**
 * A test environment for Google App Engine
 *
 * @author JB
 */
public class TestEnvironment implements ApiProxy.Environment {
    public String getAppId() {
        return "memwords";
    }

    public String getVersionId() {
        return "1.0";
    }

    public String getEmail() {
        throw new UnsupportedOperationException();
    }

    public boolean isLoggedIn() {
        throw new UnsupportedOperationException();
    }

    public boolean isAdmin() {
        throw new UnsupportedOperationException();
    }

    public String getAuthDomain() {
        throw new UnsupportedOperationException();
    }

    public String getRequestNamespace() {
        return "";
    }

    public Map<String, Object> getAttributes() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("com.google.appengine.server_url_key", "http://localhost:8888");
        return map;
    }

    /**
     * Installs a new test environment for the current thread, and configures
     * the datastore to avoid persisting on disk, and to start with a clear database
     */
    public static void install() {
        // install the environment
        ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
        ApiProxyLocalImpl proxy = new ApiProxyLocalImpl(new File("test/temp")){};
        ApiProxy.setDelegate(proxy);

        // configure datastore to avoid persisting on disk
        proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
    }

    public static void uninstall() {
        // clear profile.
        ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
        LocalDatastoreService datastoreService =
            (LocalDatastoreService) proxy.getService(LocalDatastoreService.PACKAGE);
        datastoreService.clearProfiles();

        ApiProxy.setDelegate(null);
        ApiProxy.setEnvironmentForCurrentThread(null);
    }
}
