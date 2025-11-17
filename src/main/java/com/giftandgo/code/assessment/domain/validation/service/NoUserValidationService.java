package com.giftandgo.code.assessment.domain.validation.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequest;

import java.util.List;

public class NoUserValidationService implements UserValidationService {
    @Override
    public UserValidationResult validateUsers(List<UserRequest> userRequests) {
        return UserValidationResult.ok();
    }
}
