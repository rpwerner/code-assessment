package com.giftandgo.code.assessment.domain.validation.annotation;

import com.giftandgo.code.assessment.domain.validation.CanBeDoubleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CanBeDoubleValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CanBeDouble {
    String message() default "Value cannot be converted to double";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}