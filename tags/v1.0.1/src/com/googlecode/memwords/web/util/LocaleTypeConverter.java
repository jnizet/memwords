package com.googlecode.memwords.web.util;

import java.util.Collection;
import java.util.Locale;

import net.sourceforge.stripes.validation.ScopedLocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Type converter which transforms strings into Locale instances
 * @author JB
 *
 */
public class LocaleTypeConverter implements TypeConverter<Locale> {

    @Override
    public Locale convert(String input, Class<? extends Locale> targetType, Collection<ValidationError> errors) {
        try {
            if (StringUtils.isBlank(input)) {
                return null;
            }
            return LocaleUtils.toLocale(input);
        }
        catch (IllegalArgumentException e) {
            errors.add(new ScopedLocalizableError("converter.locale", "invalidLocale"));
            return null;
        }
    }

    @Override
    public void setLocale(Locale locale) {
        // nothing to do
    }
}
