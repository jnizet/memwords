package com.googlecode.memwords.web.cards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.ResponseTooLargeException;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;

/**
 * Action bean used to create a new card
 * @author JB
 */
public class CreateCardActionBean extends BaseCardsActionBean implements ValidationErrorHandler {
	
	private URLFetchService urlFetchService;
	
	@Validate(required = true)
	private String name;
	
	@Validate(required = true)
	private String login;
	
	@Validate(required = true)
	private String password;
	
	private String url;
	
	private String iconUrl;
	
	@Inject
	public CreateCardActionBean(CardService cardService, URLFetchService urlFetchService) {
		super(cardService);
		this.urlFetchService = urlFetchService;
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
		this.iconUrl = findIconUrl(this.url);
		return new ForwardResolution("/cards/icon.jspf");
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

	protected String findIconUrl(String urlAsString) {
		if (!urlAsString.startsWith("http://") && !urlAsString.startsWith("https://")) {
			return "Bad URL";
		}
		
		try {
			URL url = new URL(urlAsString);
			try {
				HTTPResponse response = urlFetchService.fetch(url);
				if (response == null) {
					return "No response";
				}
				FavIconSaxParser parser = new FavIconSaxParser(url);
				try {
					parser.parse(new InputSource(new ByteArrayInputStream(response.getContent())));
					if (parser.getIconUrl() != null) {
						return parser.getIconUrl();
					}
					try {
						URI baseUri = url.toURI();
						URI defaultFavIconUri = baseUri.resolve("/favicon.ico");
						response = urlFetchService.fetch(defaultFavIconUri.toURL());
						if (response == null) {
							return "default favicon";
						}
						else {
							return defaultFavIconUri.toString();
						}
					}
					catch (URISyntaxException e) {
						return "URISyntaxException";
					}
				}
				catch (SAXException e) {
					return "SAXException";
				}
			}
			catch (IOException e) {
				e.printStackTrace();
				return "IOException";
			}
			catch (ResponseTooLargeException e) {
				return "ResponseTooLargeException";
			}
		}
		catch (MalformedURLException e) {
			return "Malformed URL";
		}
	}
}
