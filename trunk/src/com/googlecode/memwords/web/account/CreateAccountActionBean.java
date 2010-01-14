package com.googlecode.memwords.web.account;

import javax.crypto.SecretKey;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.AccountService;
import com.googlecode.memwords.web.IndexActionBean;
import com.googlecode.memwords.web.MwActionBean;


public class CreateAccountActionBean extends MwActionBean {

	private AccountService accountService;
	
	@Validate(required = true)
	private String userId;
	
	@Validate(required = true, minlength = 4)
	private String masterPassword;
	
	@Validate(required = true, expression="this == masterPassword")
	private String masterPassword2;
	
	@Inject
	public CreateAccountActionBean(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@DefaultHandler
	@DontValidate
	public Resolution view() {
		return new ForwardResolution("/account/createAccount.jsp");
	}
	
	public Resolution createAccount() {
		SecretKey encryptionKey = 
			accountService.createAccount(userId, 
					                     masterPassword, 
					                     getContext().getSessionId());
		getContext().setUserInformation(new UserInformation(userId, encryptionKey));
		// TODO change resolution to cards index
		return new RedirectResolution(IndexActionBean.class);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMasterPassword() {
		return masterPassword;
	}

	public void setMasterPassword(String masterPassword) {
		this.masterPassword = masterPassword;
	}

	public String getMasterPassword2() {
		return masterPassword2;
	}

	public void setMasterPassword2(String masterPassword2) {
		this.masterPassword2 = masterPassword2;
	}
}
