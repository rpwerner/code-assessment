package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.domain.validation.service.UserValidationResult;
import com.giftandgo.code.assessment.domain.validation.service.UserValidationService;
import com.giftandgo.code.assessment.web.controller.dto.UserRequest;
import com.giftandgo.code.assessment.web.exception.UserValidationException;
import com.giftandgo.code.assessment.web.service.OutcomeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserFileService {

    private UserFileParsingService userFileParsingService;

    private UserValidationService userValidationService;

    private OutcomeService outcomeService;

    public byte[] processUsers(byte[] usersInBytes) {
        List<UserRequest> userRequests = userFileParsingService.parseAndReadUsers(usersInBytes);

        UserValidationResult result = userValidationService.validateUsers(userRequests);

        if (!result.isValid()) {
            throw new UserValidationException("Validation failed", result.getValidationErrors());
        }

        return outcomeService.generateOutcomeData(userRequests);
    }
}
