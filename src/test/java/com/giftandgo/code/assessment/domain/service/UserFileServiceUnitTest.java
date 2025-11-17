package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.domain.validation.service.UserValidationResult;
import com.giftandgo.code.assessment.domain.validation.service.UserValidationService;
import com.giftandgo.code.assessment.web.exception.UserValidationException;
import com.giftandgo.code.assessment.web.service.OutcomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserFileServiceUnitTest {

    @InjectMocks
    UserFileService userFileService;

    @Mock
    UserFileParsingService userFileParsingService;

    @Mock
    UserValidationService userValidationService;

    @Mock
    OutcomeService outcomeService;

    @Test
    public void givenUsersInByteArray_whenValidationFailed_thenUserValidationException() {
        doReturn(UserValidationResult.failed(List.of("error"))).when(userValidationService).validateUsers(any());
        assertThrows(UserValidationException.class, () -> userFileService.processUsers("users".getBytes()));
    }

    @Test
    public void givenUsersInByteArray_whenValidationNotFailed_thenOutcomeInBytes() {
        doReturn(UserValidationResult.ok()).when(userValidationService).validateUsers(any());

        byte[] expectedOutcomeJson = "outcomeJson".getBytes(StandardCharsets.UTF_8);
        doReturn(expectedOutcomeJson).when(outcomeService).generateOutcomeData(any());

        byte[] usersInBytes = "users".getBytes();
        byte[] outcomeJson = userFileService.processUsers(usersInBytes);
        assertEquals(expectedOutcomeJson, outcomeJson);
    }
}
