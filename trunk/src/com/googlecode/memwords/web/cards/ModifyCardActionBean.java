package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ScopedLocalizableError;
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
 * Action bean used to modify a card
 * @author JB
 */
@HttpCache(allow = false)
public class ModifyCardActionBean extends AbstractEditCardActionBean implements ValidationErrorHandler {

    /**
     * Hidden input field containing the ID of the card to modify
     */
    @Validate(required = true)
    private String cardId;

    /**
     * Flag indicating if the password must be changed, stored in a hidden field because IE doesn't
     * support populating password fields.
     * If JavaScript is used, it's automatically set to <code>true</code> and hidden at client side,
     * because the password field can be populated with JavaScript.
     * Otherwise, it's <code>false</code> by default, because the password can't be populated.
     */
    private boolean changePassword;

    /**
     * Input field containing the password
     */
    private String password;

    /**
     * Constructor
     * @param cardService the card service
     */
    @Inject
    public ModifyCardActionBean(CardService cardService) {
        super(cardService);
    }

    /**
     * Displays the modify card page
     * @return a forward resolution to the modify card page
     */
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        loadCards();
        loadCardDetails();
        return new ForwardResolution("/cards/modifyCard.jsp");
    }

    /**
     * Displays the modify card page using AJAX
     * @return a forward resolution which updates the source page with a card details section
     * containing the modify card form.
     */
    @DontValidate
    public Resolution ajaxView() {
        loadCardDetails();
        return new ForwardResolution("/cards/ajaxModifyCard.jsp");
    }

    /**
     * Modifies the card
     * @return a redirect resolution to the cards page, with a success message
     */
    public Resolution modifyCard() {
        doModifyCard();
        return new RedirectResolution(CardsActionBean.class);
    }

    /**
     * Modifies the card, using AJAX
     * @return a forward resolution which updates the source page with the new card list
     * and the default card details section, and a success message
     */
    public Resolution ajaxModifyCard() {
        doModifyCard();
        loadCards();
        return new ForwardResolution("/cards/ajaxCards.jsp");
    }

    /**
     * Loads the card details
     */
    protected void loadCardDetails() {
        CardDetails card =
            cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        this.name = card.getName();
        this.login = card.getLogin();
        this.password = card.getPassword();
        this.url = card.getUrl();
        this.iconUrl = card.getIconUrl();
        this.note = card.getNote();
    }

    /**
     * Performs the card modification
     */
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

    /**
     * Custom validation method which checks that a password is entered if the change password
     * checkbox is checked.
     * @param errors the errors to update
     */
    @ValidationMethod(on = {"modifyCard", "ajaxModifyCard"}, when = ValidationState.ALWAYS)
    public void validatePassword(ValidationErrors errors) {
        if (changePassword && password == null) {
            errors.add("password", new ScopedLocalizableError("validation.required", "valueNotPresent"));
        }
    }

    /**
     * Custom validation method which checks that there is no other card with the same name
     * @param errors the errors to update
     */
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

    /**
     * custom error handling method used to reload the cards list if AJAX is not used,
     * and return the appropriate resolution in case of an error when AJAX is used.
     * @param errors the errors to handle
     * @return the appropriate resolution
     */
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

    /**
     * Gets the card ID
     * @return the card ID
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Sets the card ID
     * @param cardId the new card ID
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * Gets the change password flag
     * @return the change password flag
     */
    public boolean isChangePassword() {
        return changePassword;
    }

    /**
     * Sets the change password flag
     * @param changePassword the new change password flag
     */
    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    /**
     * Gets the password
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password the new password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
