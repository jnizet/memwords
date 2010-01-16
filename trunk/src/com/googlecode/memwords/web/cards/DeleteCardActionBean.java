package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;

/**
 * Action Bean used to delete a card
 * @author JB
 */
public class DeleteCardActionBean extends BaseCardsActionBean {

	@Validate(required = true)
	private String cardId;
	
	private CardDetails card;
	
	@Inject
	public DeleteCardActionBean(CardService cardService) {
		super(cardService);
	}
	
	@DefaultHandler
	public Resolution view() {
		loadCards();
		this.card = cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
		return new ForwardResolution("/cards/deleteCard.jsp");
	}
	
	public Resolution deleteCard() {
		cardService.deleteCard(cardId);
		getContext().getMessages().add(new SimpleMessage("The card has been deleted"));
		return new RedirectResolution(CardsActionBean.class);
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
}
