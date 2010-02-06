package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action Bean used to delete a card
 * @author JB
 */
@HttpCache(allow = false)
public class DeleteCardActionBean extends BaseCardsActionBean {

    /**
     * Hidden input field containing the ID od the card to delete
     */
    @Validate(required = true)
    private String cardId;

    /**
     * The details of the card to delete
     */
    private CardDetails card;

    /**
     * Constructor
     * @param cardService the card service
     */
    @Inject
    public DeleteCardActionBean(CardService cardService) {
        super(cardService);
    }

    /**
     * Displays the delete card page
     * @return a forward resolution to the delete card page
     */
    @DefaultHandler
    public Resolution view() {
        loadCards();
        this.card = cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        return new ForwardResolution("/cards/deleteCard.jsp");
    }

    /**
     * Updates the card details of the source page with the card deletion form, using AJAX
     * @return a forward resolution which displays the delete card form in the source page
     */
    public Resolution ajaxView() {
        this.card = cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        return new ForwardResolution("/cards/ajaxDeleteCard.jsp");
    }

    /**
     * Deletes the card
     * @return a redirect resolution to the cards page, with a success message
     */
    public Resolution deleteCard() {
        doDeleteCard();
        return new RedirectResolution(CardsActionBean.class);
    }

    /**
     * Deletes the card using AJAX
     * @return a forward resolution which updates the source page with the new card list,
     * the default card details section, and a success message
     */
    public Resolution ajaxDeleteCard() {
        doDeleteCard();
        loadCards();
        return new ForwardResolution("/cards/ajaxCards.jsp");
    }

    /**
     * Performs the card deletion
     */
    protected void doDeleteCard() {
        cardService.deleteCard(cardId);
        getContext().getMessages().add(new ScopedLocalizableMessage(DeleteCardActionBean.class,
                                                                    "cardDeleted"));
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
     * Gets the card details
     * @return the card details
     */
    public CardDetails getCard() {
        return card;
    }
}
