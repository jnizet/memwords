package com.googlecode.memwords.web.preferences;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;

import com.google.inject.Inject;
import com.googlecode.memwords.domain.PasswordGenerationPreferences;
import com.googlecode.memwords.domain.Preferences;
import com.googlecode.memwords.domain.UserInformation;
import com.googlecode.memwords.facade.account.AccountService;
import com.googlecode.memwords.web.MwActionBean;
import com.googlecode.memwords.web.util.ScopedLocalizableMessage;

/**
 * Action bean used to change preferences regarding random password generation
 * @author JB
 */
public class ChangePasswordGenerationPreferencesActionBean extends MwActionBean implements ValidationErrorHandler {

    /**
     * Input field containing the preferred length
     */
    @Validate(required = true)
    private int length;

    /**
     * Input field : include lower-case letters in generated passwords or not
     */
    private boolean lowerCaseLettersIncluded;

    /**
     * Input field : include upper-case letters in generated passwords or not
     */
    private boolean upperCaseLettersIncluded;

    /**
     * Input field : include digits in generated passwords or not
     */
    private boolean digitsIncluded;

    /**
     * Input field : include special characters in generated passwords or not
     */
    private boolean specialCharactersIncluded;

    /**
     * The account service
     */
    private AccountService accountService;

    /**
     * Constructor
     * @param accountService the account service
     */
    @Inject
    public ChangePasswordGenerationPreferencesActionBean(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the change password generation preferences page
     * @return a forward resolution to the change password generation preferences page
     */
    @DefaultHandler
    @DontBind
    public Resolution view() {
        loadPreferences();
        return new ForwardResolution("/preferences/changePasswordGenerationPreferences.jsp");
    }

    /**
     * Displays the change password generation preferences section using AJAX
     * @return a forward resolution which updates the preferences page with the
     * change password generation preferences form
     */
    @DontBind
    public Resolution ajaxView() {
        loadPreferences();
        return new ForwardResolution("/preferences/ajaxChangePasswordGenerationPreferences.jsp");
    }

    /**
     * Loads the password generation preference
     */
    private void loadPreferences() {
        PasswordGenerationPreferences preferences =
            getContext().getUserInformation().getPreferences().getPasswordGenerationPreferences();
        this.length = preferences.getLength();
        this.lowerCaseLettersIncluded = preferences.isLowerCaseLettersIncluded();
        this.upperCaseLettersIncluded = preferences.isUpperCaseLettersIncluded();
        this.digitsIncluded = preferences.isDigitsIncluded();
        this.specialCharactersIncluded = preferences.isSpecialCharactersIncluded();
    }

    /**
     * Changes the password generation preferences
     * @return a redirect resolution to the preferences page, with a success message
     */
    public Resolution change() {
        doChange();
        return new RedirectResolution(PreferencesActionBean.class);
    }

    /**
     * Changes the password generation preferences using AJAX
     * @return a forward resolution which updates the preferences page with a success message
     * and a clean preferences page
     */
    public Resolution ajaxChange() {
        doChange();
        return new ForwardResolution("/preferences/ajaxPreferences.jsp");
    }

    /**
     * Performs the change
     */
    private void doChange() {
        UserInformation userInformation = getContext().getUserInformation();
        Preferences preferences = userInformation.getPreferences();

        PasswordGenerationPreferences newGenerationPreferences =
            new PasswordGenerationPreferences(this.length,
                                              this.lowerCaseLettersIncluded,
                                              this.upperCaseLettersIncluded,
                                              this.digitsIncluded,
                                              this.specialCharactersIncluded);
        Preferences newPreferences =
            preferences.withPasswordGenerationPreferences(newGenerationPreferences);
        accountService.changePreferences(userInformation.getUserId(),
                                         newPreferences);
        getContext().setUserInformation(
            userInformation.withPreferences(newPreferences));
        getContext().getMessages().add(
            new ScopedLocalizableMessage(ChangePasswordGenerationPreferencesActionBean.class,
                                         "passwordGenerationPreferencesChanged"));
    }

    /**
     * Custom validation method which checks that at least one kind of characters is
     * included
     * @return <code>null</code>
     */
    @ValidationMethod(on = {"change", "ajaxChange"}, when = ValidationState.ALWAYS)
    public Resolution validateAtLeastOneKindIncluded(ValidationErrors errors) {
        if (!(lowerCaseLettersIncluded
              || upperCaseLettersIncluded
              || digitsIncluded
              || specialCharactersIncluded)) {
            errors.add(ValidationErrors.GLOBAL_ERROR, new LocalizableError("atLeastOneKinfMustBeIncluded"));
        }
        return null;
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) {
        if ("ajaxChange".equals(getContext().getEventName())) {
            return new ForwardResolution("/preferences/ajaxChangePasswordGenerationPreferences.jsp");
        }
        return null;
    }

    /**
     * Cancels the change
     * @return a redirect resolution to the preferences page
     */
    @DontBind
    public Resolution cancel() {
        return new RedirectResolution(PreferencesActionBean.class);
    }

    /**
     * Gets the value of the length input field
     * @return the value of the length input field
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the value of the length input field
     * @param length the new length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Gets the value of the "include lower-case letters" input field
     * @return the value of the "include lower-case letters" input field
     */
    public boolean isLowerCaseLettersIncluded() {
        return lowerCaseLettersIncluded;
    }

    /**
     * Sets the value of the "include lower-case letters" input field
     * @param lowerCaseLettersIncluded the new value
     */
    public void setLowerCaseLettersIncluded(boolean lowerCaseLettersIncluded) {
        this.lowerCaseLettersIncluded = lowerCaseLettersIncluded;
    }

    /**
     * Gets the value of the "include upper-case letters" input field
     * @return the value of the "include upper-case letters" input field
     */
    public boolean isUpperCaseLettersIncluded() {
        return upperCaseLettersIncluded;
    }

    /**
     * Sets the value of the "include upper-case letters" input field
     * @param upperCaseLettersIncluded the new value
     */
    public void setUpperCaseLettersIncluded(boolean upperCaseLettersIncluded) {
        this.upperCaseLettersIncluded = upperCaseLettersIncluded;
    }

    /**
     * Gets the value of the "include digits" input field
     * @return the value of the "include digits" input field
     */
    public boolean isDigitsIncluded() {
        return digitsIncluded;
    }

    /**
     * Sets the value of the "include digits" input field
     * @param digitsIncluded the new value
     */
    public void setDigitsIncluded(boolean digitsIncluded) {
        this.digitsIncluded = digitsIncluded;
    }

    /**
     * Gets the value of the "include special characters" input field
     * @return the value of the "include special characters" input field
     */
    public boolean isSpecialCharactersIncluded() {
        return specialCharactersIncluded;
    }

    /**
     * Sets the value of the "include special characters" input field
     * @param specialCharactersIncluded the new value
     */
    public void setSpecialCharactersIncluded(boolean specialCharactersIncluded) {
        this.specialCharactersIncluded = specialCharactersIncluded;
    }
}
