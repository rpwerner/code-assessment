package com.giftandgo.code.assessment.domain.validation;

import com.giftandgo.code.assessment.domain.validation.service.NoUserValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class NoUserValidationServiceUnitTest {

    NoUserValidationService noUserValidationService = new NoUserValidationService();

    @Test
    public void givenListOfDTOs_thenUserValidationOk() {
        assertTrue(noUserValidationService.validateUsers(List.of()).isValid());
    }

}
