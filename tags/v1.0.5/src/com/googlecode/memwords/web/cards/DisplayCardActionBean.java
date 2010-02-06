package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;

/**
 * Action bean used to display the details of a card
 * @author JB
 */
@HttpCache(allow = false)
public class DisplayCardActionBean extends BaseCardsActionBean {

    /**
     * Hidden input field containing the ID of the card to display
     */
    @Validate(required = true)
    private String cardId;

    /**
     * The details of the card
     */
    private CardDetails card;

    /**
     * Constructor
     * @param cardService the card service
     */
    @Inject
    public DisplayCardActionBean(CardService cardService) {
        super(cardService);
    }

    /**
     * Displays the card details page
     * @return a forward resolution to the card details page
     */
    @DefaultHandler
    public Resolution view() {
        loadCards();
        this.card = cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        return new ForwardResolution("/cards/cardDetails.jsp");
    }

    /**
     * Displays the card details, using AJAX
     * @return a forward resolution which updates the source page with a card details
     * section displaying the card details
     */
    public Resolution ajaxView() {
        this.card = cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        return new ForwardResolution("/cards/ajaxCardDetails.jsp");
    }

    /**
     * Gets the cardId
     * @return the cardId
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Sets the cardId
     * @param cardId the new cardId
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * Gets the card details
     * @return the card details
     */
    public CardDetails getCard() {
        return card;
    }

    /**
     * Indicates if the password must be unmasked or not
     * @return <code>true</code> if the password must be unmasked, <code>false</code> otherwise
     */
    public boolean isPasswordsUnmasked() {
        return getContext().getUserInformation().getPreferences().isPasswordsUnmasked();
    }
}
