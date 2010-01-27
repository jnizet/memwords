package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
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
public class CreateCardActionBean extends BaseEditCardActionBean implements ValidationErrorHandler {

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
        loadFavIconUrlIfNecessary();
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
}
