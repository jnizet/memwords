package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import com.google.inject.Inject;
import com.googlecode.memwords.facade.cards.CardService;

/**
 * Action bean used to display all the cards of an account
 * @author JB
 */
public class CardsActionBean extends BaseCardsActionBean {
	
	@Inject
	public CardsActionBean(CardService cardService) {
		super(cardService);
	}
	
	@DefaultHandler
	public Resolution view() {
		loadCards();
		return new ForwardResolution("/cards/cards.jsp");
	}
	
	public Resolution ajaxView() {
		loadCards();
		return new ForwardResolution("/cards/ajaxCards.jsp");
	}
}
