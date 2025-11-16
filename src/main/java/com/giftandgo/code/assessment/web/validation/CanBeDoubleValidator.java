package com.giftandgo.code.assessment.web.validation;

import com.giftandgo.code.assessment.web.validation.annotation.CanBeDouble;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CanBeDoubleValidator implements ConstraintValidator<CanBeDouble, String> {

    @Override
    public boolean isValid(String doubleValue, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Double.parseDouble(doubleValue);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
