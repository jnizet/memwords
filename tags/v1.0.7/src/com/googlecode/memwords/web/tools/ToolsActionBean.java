package com.googlecode.memwords.web.tools;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.Resolution;

import com.googlecode.memwords.domain.MwConstants;
import com.googlecode.memwords.domain.PasswordGenerationPreferences;
import com.googlecode.memwords.web.MwActionBean;

/**
 * Action bean for the tools page
 * @author JB
 */
@HttpCache(allow = false)
public class ToolsActionBean extends MwActionBean {

    /**
     * The password generation preferences.
     */
    private PasswordGenerationPreferences passwordGenerationPreferences;

    /**
     * The preferences regading password masking
     */
    private boolean passwordsUnmasked;

    /**
     * Displays the tools page
     * @return a forward resolution to the tools page
     */
    @DefaultHandler
    public Resolution view() {
        if (getContext().isLoggedIn()) {
            this.passwordGenerationPreferences =
                getContext().getUserInformation().getPreferences().getPasswordGenerationPreferences();
            this.passwordsUnmasked = getContext().getUserInformation().getPreferences().isPasswordsUnmasked();
        }
        else {
            this.passwordGenerationPreferences = MwConstants.DEFAULT_PASSWORD_GENERATION_PREFERENCES;
        }
        return new ForwardResolution("/tools/tools.jsp");
    }

    /**
     * Gets the password generation preferences.
     * @return the password generation preferences of the current user, if logged in, or
     * the default ones if not logged in.
     */
    public PasswordGenerationPreferences getPasswordGenerationPreferences() {
        return passwordGenerationPreferences;
    }

    /**
     * Gets the preference regarding password masking
     * @return the password masking preference of the current user, if logged in, or
     * the default one if not logged in.
     */
    public boolean isPasswordsUnmasked() {
        return passwordsUnmasked;
    }
}
