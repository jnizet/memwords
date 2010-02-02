package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ScopedLocalizableError;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to modify a card
 * @author JB
 */
public class ModifyCardActionBean extends AbstractEditCardActionBean implements ValidationErrorHandler {

    private String cardId;

    /**
     * Flag indicating if the password must be changed, stored in a hidden field because IE doesn't
     * support populating password fields
     */
    private boolean changePassword;

    private String password;

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
        loadFavIconUrlIfNecessary(cardId);
        CardDetails cardDetails = new CardDetails(cardId, name, login, password, url, iconUrl, note);
        cardService.modifyCard(
                cardDetails,
                getContext().getUserInformation().getEncryptionKey(),
                changePassword);
        getContext().getMessages().add(new ScopedLocalizableMessage(ModifyCardActionBean.class,
                                                                    "cardModified",
                                                                    cardDetails.getName()));
    }

    @ValidationMethod(on = {"modifyCard", "ajaxModifyCard"}, when = ValidationState.ALWAYS)
    public void validatePassword(ValidationErrors errors) {
        if (changePassword && password == null) {
            errors.add("password", new ScopedLocalizableError("validation.required", "valueNotPresent"));
        }
    }

    @ValidationMethod(on = {"modifyCard", "ajaxModifyCard"}, when = ValidationState.ALWAYS)
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
        String eventName = getContext().getEventName();
        if ("modifyCard".equals(eventName)) {
            loadCards();
            return null;
        }
        else if ("ajaxModifyCard".equals(eventName)) {
            return new ForwardResolution("/cards/ajaxModifyCard.jsp");
        }
        return null;
    }

    @Override
    public boolean isModification() {
        return true;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
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
