package com.googlecode.memwords.web.loginhistory;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.HistoricLogin;
import com.googlecode.memwords.facade.loginhistory.LoginHistoryService;
import com.googlecode.memwords.web.MwActionBean;

/**
 * Action bean used to display the login history
 * @author JB
 */
public class LoginHistoryActionBean extends MwActionBean {

    /**
     * The login history
     */
    private List<HistoricLogin> history;

    /**
     * The login history service
     */
    private LoginHistoryService loginHistoryService;

    /**
     * Constructor
     * @param loginHistoryService the login history service
     */
    @Inject
    public LoginHistoryActionBean(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    /**
     * Displays the login history page
     * @return a forward resolution to the login history page
     */
    @DefaultHandler
    public Resolution view() {
        history = loginHistoryService.getLoginHistory(getContext().getUserInformation().getUserId());
        return new ForwardResolution("/loginhistory/loginHistory.jsp");
    }

    /**
     * Gets the login history
     * @return the login history
     */
    public List<HistoricLogin> getHistory() {
        return history;
    }
}
