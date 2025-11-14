package com.giftandgo.code.assessment.service;

import com.giftandgo.code.assessment.exception.UserValidationException;
import com.giftandgo.code.assessment.dto.UserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class DefaultUserValidationService implements UserValidationService {

    @Autowired
    Validator validator;

    @Override
    public void validateUsers(List<UserDTO> userDTOS) {
        for (UserDTO user : userDTOS) {
            Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new UserValidationException("Error while validating users", violations);
            }
        }
    }
}
