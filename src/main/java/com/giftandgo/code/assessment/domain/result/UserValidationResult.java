package com.giftandgo.code.assessment.domain.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class UserValidationResult {

    boolean isValid;
    List<String> validationErrors;

    public static UserValidationResult ok() {
        return UserValidationResult
                .builder()
                .isValid(true)
                .validationErrors(List.of())
                .build();
    }

    public static UserValidationResult failed(List<String> errors) {
        return UserValidationResult
                .builder()
                .isValid(false)
                .validationErrors(errors)
                .build();
    }
}
