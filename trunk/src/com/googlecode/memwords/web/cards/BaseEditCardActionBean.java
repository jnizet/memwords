package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.facade.cards.FavIconException;

public class BaseEditCardActionBean extends BaseCardsActionBean {
	
	@Validate(required = true)
	protected String name;
	
	@Validate(required = true)
	protected String login;
	
	@Validate(required = true)
	protected String password;
	
	protected String url;
	
	protected String iconUrl;
	
	@Inject
	public BaseEditCardActionBean(CardService cardService) {
		super(cardService);
	}
	
	@DontValidate
	public Resolution ajaxGetIcon() {
		try {
			this.iconUrl = cardService.findFavIconUrl(this.url);
			if (this.iconUrl == null) {
				getContext().getValidationErrors().addGlobalError(new SimpleError("This web site has no icon. The default icon will be used."));
			}
		}
		catch (FavIconException e) {
			getContext().getValidationErrors().addGlobalError(new SimpleError("An error occurred while getting the icon of the web site. The default icon will be used."));
		}
		return new ForwardResolution("/cards/_icon.jsp");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
