package com.googlecode.memwords.web.cards;

import java.util.List;

import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardBasicInformation;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.web.MwActionBean;

public class BaseCardsActionBean extends MwActionBean {
	
	protected CardService cardService;
	private List<CardBasicInformation> cards;
	
	@Inject
	public BaseCardsActionBean(CardService cardService) {
		this.cardService = cardService;
	}
	
	@DontBind
	public Resolution cancel() {
		return new RedirectResolution(CardsActionBean.class);
	}
	
	protected void loadCards() {
		this.cards = cardService.getCards(getContext().getUserInformation().getUserId());
	}
	
	public List<CardBasicInformation> getCards() {
		return cards;
	}
}
