package cn.gomro.commons.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * @author adam
 */
public class DateGreaterThanValidator implements ConstraintValidator<DateGreaterThan, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value instanceof LocalDateTime) {
            LocalDateTime param = (LocalDateTime) value;
            return LocalDateTime.now().isBefore(param);
        }

        return false;
    }
}
