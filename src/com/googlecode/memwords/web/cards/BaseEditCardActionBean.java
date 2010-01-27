package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ScopedLocalizableError;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.facade.cards.FavIconException;

/**
 * Base class for the action beans handling card creation and modification, providing common
 * methods ant attributes.
 * @author JB
 */
public class BaseEditCardActionBean extends BaseCardsActionBean {

    @Validate(required = true)
    protected String name;

    @Validate(required = true)
    protected String login;

    @Validate(required = true)
    protected String password;

    protected String url;

    protected String iconUrl;

    protected String note;

    /**
     * Hidden field indicating if an ajax request has already tried to retrieve the icon URL
     */
    protected boolean iconUrlFetched;

    @Inject
    public BaseEditCardActionBean(CardService cardService) {
        super(cardService);
    }

    @DontValidate
    public Resolution ajaxGetIcon() {
        try {
            this.iconUrl = cardService.findFavIconUrl(this.url);
            if (this.iconUrl == null) {
                getContext().getValidationErrors().addGlobalError(
                    new ScopedLocalizableError(BaseEditCardActionBean.class.getName(),
                                               "noIconFound"));
            }
        }
        catch (FavIconException e) {
            getContext().getValidationErrors().addGlobalError(
                new ScopedLocalizableError(BaseEditCardActionBean.class.getName(),
                                           "errorWhileFetchingIcon"));
        }
        return new ForwardResolution("/cards/_icon.jsp");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isIconUrlFetched() {
        return iconUrlFetched;
    }

    public void setIconUrlFetched(boolean iconUrlFetched) {
        this.iconUrlFetched = iconUrlFetched;
    }

    protected void loadFavIconUrlIfNecessary() {
        if (!iconUrlFetched && this.url != null) {
            try {
                this.iconUrl = cardService.findFavIconUrl(this.url);
            }
            catch (FavIconException e) {
                // ignore : the fav icon URL stays null
            }
        }
    }
}
