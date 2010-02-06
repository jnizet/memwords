package com.googlecode.memwords.web.util;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.LocalizableMessage;

/**
 * A localizable message attached to an action bean
 * @author JB
 */
public class ScopedLocalizableMessage extends LocalizableMessage {
    /**
     * Constructor. The full key of the message is in the form
     * <code>&lt;actionBeanClassFQN&gt;.&lt;messageKey&gt;</code>
     * @param actionBeanClass the action bean class the message is attached to
     * @param messageKey the message key, appended after the action bean class name
     * in order to form the full message key
     * @param parameters the parameters of the message
     */
    public ScopedLocalizableMessage(Class<? extends ActionBean> actionBeanClass,
                                    String messageKey,
                                    Object... parameters) {
        super(actionBeanClass.getName() + "." + messageKey, parameters);
    }
}
