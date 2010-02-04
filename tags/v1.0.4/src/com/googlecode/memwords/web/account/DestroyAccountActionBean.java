package com.googlecode.memwords.web.account;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import com.google.inject.Inject;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.IndexActionBean;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to destroy an account
 * @author JB
 */
public class DestroyAccountActionBean extends MwActionBean {

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * Constructor
     * @param accountService the account service
     */
    @Inject
    public DestroyAccountActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the destroy account page
     * @return a forward resolution to the destroy account page
     */
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution("/account/destroyAccount.jsp");
    }

    /**
     * Destroys the current account
     * @return a redirect resolution to the welcome page, with a success message
     */
    public Resolution destroy() {
        accountService.destroyAccount(getContext().getUserInformation().getUserId());
        getContext().getMessages().add(new ScopedLocalizableMessage(DestroyAccountActionBean.class,
                                                                    "accountDestroyed"));
        getContext().logout();
        return new RedirectResolution(IndexActionBean.class);
    }

    /**
     * Cancels the destruction
     * @return a redirect resolution to the account page
     */
    public Resolution cancel() {
        return new RedirectResolution(AccountActionBean.class);
    }
}
