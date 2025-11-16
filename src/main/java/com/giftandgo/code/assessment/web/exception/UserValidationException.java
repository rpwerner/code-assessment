package com.giftandgo.code.assessment.web.exception;

import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserValidationException extends RuntimeException{
    private Set<ConstraintViolation<UserRequestDTO>> violations;

    public UserValidationException(String message, Set<ConstraintViolation<UserRequestDTO>> violations) {
        super(message);
        this.violations = violations;
    }
}
