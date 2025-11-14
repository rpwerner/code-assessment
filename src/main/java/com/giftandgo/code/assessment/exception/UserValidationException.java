package com.giftandgo.code.assessment.exception;

import com.giftandgo.code.assessment.dto.UserDTO;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserValidationException extends RuntimeException{
    private Set<ConstraintViolation<UserDTO>> violations;

    public UserValidationException(String message, Set<ConstraintViolation<UserDTO>> violations) {
        super(message);
        this.violations = violations;
    }
}
