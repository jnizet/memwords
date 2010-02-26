package com.googlecode.memwords.domain;

import java.io.Serializable;

/**
 * Preferences regarding password generation (immutable value object)
 * @author JB
 */
public final class PasswordGenerationPreferences implements Serializable {

    /**
     * The preferred password length
     */
    private int length;

    /**
     * Include lower-case letters in generated passwords or not
     */
    private boolean lowerCaseLettersIncluded;

    /**
     * Include upper-case letters in generated passwords or not
     */
    private boolean upperCaseLettersIncluded;

    /**
     * Include digits in generated passwords or not
     */
    private boolean digitsIncluded;

    /**
     * Include special characters in generated passwords or not
     */
    private boolean specialCharactersIncluded;

    /**
     * Constructor
     * @param length the preferred password length
     * @param lowerCaseLettersIncluded include lower-case letters in generated passwords or not
     * @param upperCaseLettersIncluded include upper-case letters in generated passwords or not
     * @param digitsIncluded include digits in generated passwords or not
     * @param specialCharactersIncluded include special characters in generated passwords or not
     */
    public PasswordGenerationPreferences(int length,
                                         boolean lowerCaseLettersIncluded,
                                         boolean upperCaseLettersIncluded,
                                         boolean digitsIncluded,
                                         boolean specialCharactersIncluded) {
        this.length = length;
        this.lowerCaseLettersIncluded = lowerCaseLettersIncluded;
        this.upperCaseLettersIncluded = upperCaseLettersIncluded;
        this.digitsIncluded = digitsIncluded;
        this.specialCharactersIncluded = specialCharactersIncluded;
    }

    /**
     * Gets the preferred length of generated passwords
     * @return the preferred length of generated passwords
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the preference regarding lower-case letters
     * @return <code>true</code> if lower-case letters must be included in generated passwords,
     * <code>false</code> otherwise
     */
    public boolean isLowerCaseLettersIncluded() {
        return lowerCaseLettersIncluded;
    }

    /**
     * Gets the preference regarding upper-case letters
     * @return <code>true</code> if upper-case letters must be included in generated passwords,
     * <code>false</code> otherwise
     */
    public boolean isUpperCaseLettersIncluded() {
        return upperCaseLettersIncluded;
    }

    /**
     * Gets the preference regarding digits
     * @return <code>true</code> if digits must be included in generated passwords,
     * <code>false</code> otherwise
     */
    public boolean isDigitsIncluded() {
        return digitsIncluded;
    }

    /**
     * Gets the preference regarding special characters
     * @return <code>true</code> if special characters must be included in generated passwords,
     * <code>false</code> otherwise
     */
    public boolean isSpecialCharactersIncluded() {
        return specialCharactersIncluded;
    }
}
