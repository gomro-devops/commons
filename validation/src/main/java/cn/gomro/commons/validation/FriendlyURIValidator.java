package cn.gomro.commons.validation;

import cn.gomro.commons.utils.RegexUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author adam
 */
public class FriendlyURIValidator implements ConstraintValidator<FriendlyURI, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return RegexUtils.check(RegexUtils.Type.IS_FRIENDLY_URI, value);
    }
}
