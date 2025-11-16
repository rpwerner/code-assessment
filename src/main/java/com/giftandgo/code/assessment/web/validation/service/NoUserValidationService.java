package com.giftandgo.code.assessment.web.validation.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;

import java.util.List;

public class NoUserValidationService implements UserValidationService {
    @Override
    public void validateUsers(List<UserRequestDTO> userRequestDTOS) {
        // nothing to do here
    }
}
