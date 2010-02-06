package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to create a new card
 * @author JB
 */
@HttpCache(allow = false)
public class CreateCardActionBean extends AbstractEditCardActionBean implements ValidationErrorHandler {

    /**
     * Input field containing the password of the card
     */
    @Validate(required = true)
    private String password;

    /**
     * Constructor
     * @param cardService the card service
     */
    @Inject
    public CreateCardActionBean(CardService cardService) {
        super(cardService);
    }

    /**
     * Displays the create card page
     * @return a forward resolution to the create card page
     */
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        loadCards();
        return new ForwardResolution("/cards/createCard.jsp");
    }

    /**
     * Updates the card details of the source page with the card creation form, using AJAX
     * @return a forward resolution which displays the create card form in the source page
     */
    @DontValidate
    public Resolution ajaxView() {
        return new ForwardResolution("/cards/ajaxCreateCard.jsp");
    }

    /**
     * Creates the card
     * @return a redirect resolution to the cards page, with a success message
     */
    public Resolution createCard() {
        doCreateCard();
        return new RedirectResolution(CardsActionBean.class);
    }

    /**
     * Creates a card using AJAX
     * @return a forward resolution which updates the source page with the new cards list
     * and the default card details section in the source page, as well as a success message
     */
    public Resolution ajaxCreateCard() {
        doCreateCard();
        loadCards();
        return new ForwardResolution("/cards/ajaxCards.jsp");
    }

    /**
     * Performs the card creation
     */
    protected void doCreateCard() {
        loadFavIconUrlIfNecessary(null);
        CardDetails cardDetails = new CardDetails(null, name, login, password, url, iconUrl, note);
        cardService.createCard(
                getContext().getUserInformation().getUserId(),
                cardDetails,
                getContext().getUserInformation().getEncryptionKey());
        getContext().getMessages().add(
            new ScopedLocalizableMessage(CreateCardActionBean.class,
                                         "cardCreated",
                                         cardDetails.getName()));
    }

    /**
     * Custom validation method which checks that no card already exists with the same name
     * @param errors the errors to update
     */
    @ValidationMethod(on = {"createCard", "ajaxCreateCard"}, when = ValidationState.ALWAYS)
    public void validateNameDoesntExist(ValidationErrors errors) {
        if (!errors.containsKey("name")
            && cardService.cardExists(getContext().getUserInformation().getUserId(),
                                      name,
                                      null,
                                      getContext().getUserInformation().getEncryptionKey())) {
            errors.add("name", new LocalizableError("cardNameAlreadyExists"));
        }
    }

    /**
     * Custom error handling method used to reload the cards list if AJAX is not used,
     * and return the appropriate resolution in case of an error when AJAX is used.
     * @param errors the errors to handle
     * @return the appropriate resolution
     */
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) {
        String eventName = getContext().getEventName();
        if ("createCard".equals(eventName)) {
            loadCards();
            return null;
        }
        else if ("ajaxCreateCard".equals(eventName)) {
            return new ForwardResolution("/cards/ajaxCreateCard.jsp");
        }
        return null;
    }

    @Override
    public boolean isModification() {
        return false;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
