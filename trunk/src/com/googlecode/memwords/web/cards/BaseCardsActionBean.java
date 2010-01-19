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
 * methods to all these actions.
 * @author JB
 */
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

    @DontBind
    public Resolution ajaxCancel() {
        return new ForwardResolution("/cards/ajaxEmptyCardDetails.jsp");
    }

    protected void loadCards() {
        UserInformation userInformation = getContext().getUserInformation();
        this.cards = cardService.getCards(userInformation.getUserId(),
                                          userInformation.getEncryptionKey());
    }

    public List<CardBasicInformation> getCards() {
        return cards;
    }
}
