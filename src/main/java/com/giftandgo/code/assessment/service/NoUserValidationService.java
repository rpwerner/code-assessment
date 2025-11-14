package com.giftandgo.code.assessment.service;

import com.giftandgo.code.assessment.dto.UserDTO;

import java.util.List;

public class NoUserValidationService implements UserValidationService {
    @Override
    public void validateUsers(List<UserDTO> userDTOS) {
        // nothing to do here
    }
}
