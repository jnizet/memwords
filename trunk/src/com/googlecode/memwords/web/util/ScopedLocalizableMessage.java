package com.googlecode.memwords.web.util;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.LocalizableMessage;

/**
 * A localizable message attached to an action bean
 * @author JB
 */
public class ScopedLocalizableMessage extends LocalizableMessage {
    public ScopedLocalizableMessage(Class<? extends ActionBean> actionBeanClass,
                                    String messageKey,
                                    Object... parameters) {
        super(actionBeanClass.getName() + "." + messageKey, parameters);
    }
}
