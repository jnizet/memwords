package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;

/**
 * Action bean used to modify a    card
 * @author JB
 */
public class ModifyCardActionBean extends BaseEditCardActionBean implements ValidationErrorHandler {

    private String cardId;

    @Inject
    public ModifyCardActionBean(CardService cardService) {
        super(cardService);
    }

    @DefaultHandler
    @DontValidate
    public Resolution view() {
        loadCards();
        loadCardDetails();
        return new ForwardResolution("/cards/modifyCard.jsp");
    }

    @DontValidate
    public Resolution ajaxView() {
        loadCardDetails();
        return new ForwardResolution("/cards/ajaxModifyCard.jsp");
    }

    public Resolution modifyCard() {
        doModifyCard();
        return new RedirectResolution(CardsActionBean.class);
    }

    public Resolution ajaxModifyCard() {
        doModifyCard();
        loadCards();
        return new ForwardResolution("/cards/ajaxCards.jsp");
    }

    protected void loadCardDetails() {
        CardDetails card =
            cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        this.name = card.getName();
        this.login = card.getLogin();
        this.password = card.getPassword();
        this.url = card.getUrl();
        this.iconUrl = card.getIconUrl();
    }

    protected void doModifyCard() {
        CardDetails cardDetails = new CardDetails(cardId, name, login, password, url, iconUrl);
        cardService.modifyCard(
                cardDetails,
                getContext().getUserInformation().getEncryptionKey());
        getContext().getMessages().add(new SimpleMessage("Card {0} modified", cardDetails.getName()));
    }

    @ValidationMethod(on = {"createCard", "ajaxCreateCard"}, when = ValidationState.ALWAYS)
    public void validateNameDoesntExist(ValidationErrors errors) {
        if (!errors.containsKey("name")
            && cardService.cardExists(getContext().getUserInformation().getUserId(),
                                      name,
                                      cardId,
                                      getContext().getUserInformation().getEncryptionKey())) {
            errors.add("name", new LocalizableError("cardNameAlreadyExists"));
        }
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) {
        if ("modifyCard".equals(getContext().getEventName())) {
            loadCards();
        }
        return null;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
