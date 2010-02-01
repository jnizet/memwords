package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;

/**
 * Action Bean used to display the details of a card
 * @author JB
 */
public class DisplayCardActionBean extends BaseCardsActionBean {

    @Validate(required = true)
    private String cardId;

    private CardDetails card;

    @Inject
    public DisplayCardActionBean(CardService cardService) {
        super(cardService);
    }

    @DefaultHandler
    public Resolution view() {
        loadCards();
        this.card = cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        return new ForwardResolution("/cards/cardDetails.jsp");
    }

    public Resolution ajaxView() {
        this.card = cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
        return new ForwardResolution("/cards/ajaxCardDetails.jsp");
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public CardDetails getCard() {
        return card;
    }

    public boolean isPasswordsUnmasked() {
        return getContext().getUserInformation().getPreferences().isPasswordsUnmasked();
    }
}
