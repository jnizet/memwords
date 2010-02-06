package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;

import com.google.inject.Inject;
import com.googlecode.memwords.facade.cards.CardService;

/**
 * Action bean used to display all the cards of an account
 * @author JB
 */
@HttpCache(allow = false)
public class CardsActionBean extends BaseCardsActionBean {

    /**
     * Constructor
     * @param cardService the card service
     */
    @Inject
    public CardsActionBean(CardService cardService) {
        super(cardService);
    }

    /**
     * Displays the cards page
     * @return a forward resolution to the cards page
     */
    @DefaultHandler
    public Resolution view() {
        loadCards();
        return new ForwardResolution("/cards/cards.jsp");
    }
}
