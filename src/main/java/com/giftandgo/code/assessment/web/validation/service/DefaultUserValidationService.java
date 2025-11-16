package com.giftandgo.code.assessment.web.validation.service;

import com.giftandgo.code.assessment.web.exception.UserValidationException;
import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class DefaultUserValidationService implements UserValidationService {

    @Autowired
    Validator validator;

    @Override
    public void validateUsers(List<UserRequestDTO> userRequestDTOS) {
        for (UserRequestDTO user : userRequestDTOS) {
            Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new UserValidationException("Error while validating users", violations);
            }
        }
    }
}
