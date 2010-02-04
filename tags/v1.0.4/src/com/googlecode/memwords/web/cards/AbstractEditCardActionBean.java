package com.googlecode.memwords.web.cards;

import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.ScopedLocalizableError;
import net.sourceforge.stripes.validation.Validate;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.CardDetails;
import com.googlecode.memwords.facade.cards.CardService;
import com.googlecode.memwords.facade.cards.FavIconException;

/**
 * Base class for the action beans handling card creation and modification, providing common
 * methods ant attributes.
 * @author JB
 */
public abstract class AbstractEditCardActionBean extends BaseCardsActionBean {

    /**
     * Input field containing the name of the card
     */
    @Validate(required = true)
    protected String name;

    /**
     * Input field containing the login of the card
     */
    @Validate(required = true)
    protected String login;

    /**
     * Input field containing the URL of the card
     */
    protected String url;

    /**
     * Hidden input field containing the icon URL of the card (computed with AJAX)
     */
    protected String iconUrl;

    /**
     * Input field containing the note of the card
     */
    protected String note;

    /**
     * Hidden field indicating if an AJAX request has already tried to retrieve the icon URL.
     * Useful if <code>iconUrl</code> is <code>null</code>.
     */
    protected boolean iconUrlFetched;

    /**
     * Constructor
     * @param cardService the card service
     */
    @Inject
    public AbstractEditCardActionBean(CardService cardService) {
        super(cardService);
    }

    /**
     * Tries to fetch the icon URL based on the entered URL, using AJAX.
     * @return a forward resolution which dynamically updates the source page with the icon
     */
    @DontValidate
    public Resolution ajaxGetIcon() {
        try {
            this.iconUrl = cardService.findFavIconUrl(this.url);
            if (this.iconUrl == null) {
                getContext().getValidationErrors().addGlobalError(
                    new ScopedLocalizableError(AbstractEditCardActionBean.class.getName(),
                                               "noIconFound"));
            }
        }
        catch (FavIconException e) {
            getContext().getValidationErrors().addGlobalError(
                new ScopedLocalizableError(AbstractEditCardActionBean.class.getName(),
                                           "errorWhileFetchingIcon"));
        }
        return new ForwardResolution("/cards/_icon.jsp");
    }

    /**
     * Gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the login
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login
     * @param login the new login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password
     * @return the password
     */
    public abstract String getPassword();

    /**
     * Sets the password
     * @param password the new password
     */
    public abstract void setPassword(String password);

    /**
     * Gets the password, escaped for JavaScript
     * @return tye password, escaped for JavaScript, or an empty string if the password
     * is <code>null</code>
     */
    public String getJavaScriptEscapedPassword() {
        String password = getPassword();
        if (password == null) {
            return "";
        }
        return StringEscapeUtils.escapeJavaScript(password);
    }

    /**
     * Gets the URL
     * @return the URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL
     * @param url the new URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the icon URL
     * @return the icon URL
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets the icon URL
     * @param iconUrl the new icon URL
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Gets the note
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note
     * @param note the new note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gets the icon URL fetched flag
     * @return the icon URL fetched flag
     */
    public boolean isIconUrlFetched() {
        return iconUrlFetched;
    }

    /**
     * Sets the icon URL fetched flag
     * @param iconUrlFetched the new icon URL fetched flag
     */
    public void setIconUrlFetched(boolean iconUrlFetched) {
        this.iconUrlFetched = iconUrlFetched;
    }

    /**
     * Indicates if the concrete action bean is used to modify a card or to create one
     * @return <code>true</code> if the the concrete action bean is used to modify a card,
     * <code>false</code> otherwise
     */
    public abstract boolean isModification();

    /**
     * Loads the fav icon URL if <code>iconUrlFetched</code> is <code>false</code>
     * (which indicates that the fav icon URL has not been fetched with AJAX at client side)
     * and if <code>url</code> is different than the current URL of the card identified by
     * the given cardId (which indicates that there is no reason to reload the fav icon URL,
     * since the URL is the same as before)
     * @param cardId the ID of the card. <code>null</code> in the case of a creation.
     */
    protected void loadFavIconUrlIfNecessary(String cardId) {
        if (!iconUrlFetched) {
            String previousUrl = null;
            if (cardId != null) {
                CardDetails previousCardDetails =
                    cardService.getCardDetails(cardId, getContext().getUserInformation().getEncryptionKey());
                previousUrl = previousCardDetails.getUrl();
            }
            if (!equalOrBothNull(this.url, previousUrl)) {
                try {
                    if (this.url == null) {
                        this.iconUrl = null;
                    }
                    else {
                        this.iconUrl = cardService.findFavIconUrl(this.url);
                    }
                }
                catch (FavIconException e) {
                    // ignore : the fav icon URL stays null
                }
            }
        }
    }

    private boolean equalOrBothNull(String a, String b) {
        if (a == null) {
            return b == null;
        }
        else {
            return a.equals(b);
        }
    }
}
