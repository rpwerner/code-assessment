package com.giftandgo.code.assessment.domain.validation.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class DefaultUserValidationService implements UserValidationService {

    @Autowired
    Validator validator;

    @Override
    public UserValidationResult validateUsers(List<UserRequest> userRequests) {
        List<String> violationErrors = new ArrayList<>();
        for (UserRequest user : userRequests) {
            Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);

            violationErrors.addAll(
                    violations.stream()
                            .map(violation -> "Field: [" + violation.getPropertyPath() + "]: " + violation.getMessage())
                            .toList());
        }

        if (violationErrors.isEmpty()) {
            return UserValidationResult.ok();
        }

        return UserValidationResult.failed(violationErrors);

    }
}
