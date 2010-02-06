package com.googlecode.memwords.domain;

/**
 * Class containing global constants of the application
 * @author JB
 */
public final class MwConstants {

    /**
     * The minimum number of characters a master password may have
     */
    public static final int MASTER_PASSWORD_MIN_LENGTH = 4;

    /**
     * The GMT time zone ID
     */
    public static final String GMT = "GMT";

    /**
     * The default password generation preferences
     */
    public static final PasswordGenerationPreferences DEFAULT_PASSWORD_GENERATION_PREFERENCES =
        new PasswordGenerationPreferences(8, true, true, true, true);

    /**
     * Private constructor to prevent unnecessary instantiations
     */
    private MwConstants() {
    }
}
