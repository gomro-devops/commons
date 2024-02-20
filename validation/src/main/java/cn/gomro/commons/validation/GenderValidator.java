package cn.gomro.commons.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author adam
 */
public class GenderValidator implements ConstraintValidator<Gender, String> {

    private static final Pattern GENDER_PATTERN = Pattern.compile(
            "MALE|FEMALE"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null || value.isEmpty()) {
            return true;
        }
        Matcher m = GENDER_PATTERN.matcher(value);
        return m.matches();
    }
}
