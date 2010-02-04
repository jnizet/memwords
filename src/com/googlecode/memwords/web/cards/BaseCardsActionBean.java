package com.googlecode.memwords.web.cards;

import java.util.List;

import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardBasicInformation;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.web.MwActionBean;

/**
 * Base class for the action beans handling cards, which provide common
 * methods and fields to all these actions.
 * @author JB
 */
public class BaseCardsActionBean extends MwActionBean {

    /**
     * the card service
     */
    protected CardService cardService;

    /**
     * The list of cards to display on all the pages handling cards, when
     * JavaScript is not enabled and everything is done without AJAX
     */
    private List<CardBasicInformation> cards;

    /**
     * Constructor
     * @param cardService the card service
     */
    @Inject
    public BaseCardsActionBean(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * Cancels the current operation
     * @return a redirect resolution to the cards page
     */
    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(CardsActionBean.class);
    }

    /**
     * Cancels using AJAX
     * @return a forward resolution which redisplays the default card details section
     */
    @DontBind
    public Resolution ajaxCancel() {
        return new ForwardResolution("/cards/ajaxEmptyCardDetails.jsp");
    }

    /**
     * Loads the cards to display
     */
    protected void loadCards() {
        UserInformation userInformation = getContext().getUserInformation();
        this.cards = cardService.getCards(userInformation.getUserId(),
                                          userInformation.getEncryptionKey());
    }

    /**
     * Gets the list of cards of the current account
     * @return the list of cards of the current account
     */
    public List<CardBasicInformation> getCards() {
        return cards;
    }
}
