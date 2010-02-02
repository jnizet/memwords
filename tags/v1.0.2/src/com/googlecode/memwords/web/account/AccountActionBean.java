package com.googlecode.memwords.web.account;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import com.googlecode.memwords.web.MwActionBean;

/**
 * Action bean used to display the account actions
 * @author JB
 */
public class AccountActionBean extends MwActionBean {
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution("/account/account.jsp");
    }
}
