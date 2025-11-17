package com.giftandgo.code.assessment.domain.validation;

import com.giftandgo.code.assessment.domain.result.UserValidationResult;
import com.giftandgo.code.assessment.domain.validation.service.DefaultUserValidationService;
import com.giftandgo.code.assessment.domain.validation.service.NoUserValidationService;
import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DefaultUserValidationServiceUnitTest {

    @InjectMocks
    DefaultUserValidationService defaultUserValidationService;

    @Mock
    Validator validator;

    @Test
    public void givenListOfDTOs_whenNoViolations_thenUserValidationOk() {
        doReturn(Set.of()).when(validator).validate(any());
        assertTrue(defaultUserValidationService.validateUsers(List.of(UserRequestDTO.builder().build())).isValid());
    }

    @Test
    public void givenListOfDTOs_whenAtLeastOneViolations_thenUserValidationFailed() {

        ConstraintViolation<UserRequestDTO> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        doReturn(path).when(violation).getPropertyPath();
        doReturn("message").when(violation).getMessage();

        doReturn(Set.of(violation)).when(validator).validate(any());

        UserValidationResult userValidationResult = defaultUserValidationService.validateUsers(List.of(UserRequestDTO.builder().build()));
        assertFalse(userValidationResult.isValid());
        assertFalse(userValidationResult.getValidationErrors().isEmpty());
    }

}
