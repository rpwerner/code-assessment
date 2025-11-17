package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.domain.result.UserValidationResult;
import com.giftandgo.code.assessment.domain.validation.service.UserValidationService;
import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import com.giftandgo.code.assessment.web.exception.UserValidationException;
import com.giftandgo.code.assessment.web.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserFileService {

    @Autowired
    UserFileParsingService userFileParsingService;

    @Autowired
    UserValidationService userValidationService;

    @Autowired
    OutcomeService outcomeService;

    public byte[] processUsers(byte[] usersInBytes) {
        List<UserRequestDTO> userRequestDTOS = userFileParsingService.parseAndReadUsers(usersInBytes);

        UserValidationResult result = userValidationService.validateUsers(userRequestDTOS);

        if (!result.isValid()) {
            throw new UserValidationException("Validation failed", result.getValidationErrors());
        }

        return outcomeService.generateOutcomeData(userRequestDTOS);
    }
}
