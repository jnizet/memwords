package com.googlecode.memwords.facade.loginhistory;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.googlecode.memwords.domain.HistoricLogin;

/**
 * Service to manage historic logins
 * @author JB
 */
@ImplementedBy(LoginHistoryServiceImpl.class)
public interface LoginHistoryService {

    /**
     * Adds a login to the history of the given account
     * @param userId the user ID of the account
     * @param userAgent the user agent found in the request
     * @param ip the IP address found in the request
     */
    void addLogin(String userId, String userAgent, String ip);

    /**
     * Gets the login history for the given user ID.
     * @return a list, containing the
     * {@link com.googlecode.memwords.domain.Account#MAX_HISTORIC_LOGIN_COUNT MAX_HISTORIC_LOGIN_COUNT}
     * latest logins of the user (latest first in the list)
     */
    List<HistoricLogin> getLoginHistory(String userId);

    /**
     * Gets the latest historic login which is not the current one (i.e. the second item
     * in the list of historic logins)
     * @param userId the user ID of the account
     * @return the historic login, or <code>null</code> if it's the first one
     */
    HistoricLogin getLatestHistoricLogin(String userId);
}
