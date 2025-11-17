package com.giftandgo.code.assessment.domain.validation.service;

import com.giftandgo.code.assessment.domain.result.UserValidationResult;
import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserValidationService {
    UserValidationResult validateUsers(List<UserRequestDTO> userRequestDTOS);
}
