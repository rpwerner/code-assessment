package com.giftandgo.code.assessment.domain.validation.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequest;

import java.util.List;

public interface UserValidationService {
    UserValidationResult validateUsers(List<UserRequest> userRequests);
}
