package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.facade.cards.FavIconException;

/**
 * Action bean used to create a new card
 * @author JB
 */
public class CreateCardActionBean extends BaseCardsActionBean implements ValidationErrorHandler {
	
	@Validate(required = true)
	private String name;
	
	@Validate(required = true)
	private String login;
	
	@Validate(required = true)
	private String password;
	
	private String url;
	
	private String iconUrl;
	
	@Inject
	public CreateCardActionBean(CardService cardService) {
		super(cardService);
	}
	
	@DefaultHandler
	@DontValidate
	public Resolution view() {
		loadCards();
		return new ForwardResolution("/cards/createCard.jsp");
	}
	
	@DontValidate
	public Resolution ajaxView() {
		return new ForwardResolution("/cards/ajaxCreateCard.jsp");
	}
	
	public Resolution createCard() {
		doCreateCard();
		return new RedirectResolution(CardsActionBean.class);
	}
	
	public Resolution ajaxCreateCard() {
		doCreateCard();
		loadCards();
		return new ForwardResolution("/cards/ajaxCards.jsp");
	}

	protected void doCreateCard() {
		CardDetails cardDetails = new CardDetails(null, name, login, password, url, iconUrl);
		cardService.createCard(
				getContext().getUserInformation().getUserId(), 
                cardDetails, 
                getContext().getUserInformation().getEncryptionKey());
		getContext().getMessages().add(new SimpleMessage("Card {0} created", cardDetails.getName()));
	}
	
	@ValidationMethod(on = {"createCard", "ajaxCreateCard"}, when = ValidationState.ALWAYS)
	public void validateNameDoesntExist(ValidationErrors errors) {
		if (!errors.containsKey("name")) {
			if (cardService.cardExists(getContext().getUserInformation().getUserId(), name)) {
				errors.add("name", new LocalizableError("cardNameAlreadyExists"));
			}
		}
	}
	
	@Override
	public Resolution handleValidationErrors(ValidationErrors errors) {
		if ("createCard".equals(getContext().getEventName())) {
			loadCards();
		}
		return null;
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
