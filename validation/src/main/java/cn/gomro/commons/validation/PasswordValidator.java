package cn.gomro.commons.validation;

import cn.gomro.commons.utils.RegexUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author adam
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null || value.isEmpty()) {
            return true;
        }
        return RegexUtils.check(RegexUtils.Type.IS_PASSWD,value);
    }
}
