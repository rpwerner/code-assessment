package com.giftandgo.code.assessment.web.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class UserValidationException extends RuntimeException {
    private List<String> violations;

    public UserValidationException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }
}
