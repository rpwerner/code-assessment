package com.giftandgo.code.assessment.web.validation.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserValidationService {
    void validateUsers(List<UserRequestDTO> userRequestDTOS);
}
