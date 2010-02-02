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

    private AccountService accountService;

    @Inject
    public DestroyAccountActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution("/account/destroyAccount.jsp");
    }

    public Resolution destroy() {
        accountService.destroyAccount(getContext().getUserInformation().getUserId());
        getContext().getMessages().add(new ScopedLocalizableMessage(DestroyAccountActionBean.class,
                                                                    "accountDestroyed"));
        getContext().logout();
        return new RedirectResolution(IndexActionBean.class);
    }

    public Resolution cancel() {
        return new RedirectResolution(AccountActionBean.class);
    }
}
