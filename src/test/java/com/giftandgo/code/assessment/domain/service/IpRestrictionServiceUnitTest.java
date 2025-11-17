package com.giftandgo.code.assessment.domain.service;


import com.giftandgo.code.assessment.domain.restriction.CountryIpRestriction;
import com.giftandgo.code.assessment.domain.restriction.IpRestrictionStrategy;
import com.giftandgo.code.assessment.domain.restriction.IspIpRestriction;
import com.giftandgo.code.assessment.domain.restriction.IpRestrictionResult;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import com.giftandgo.code.assessment.web.exception.IpRestrictionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class IpRestrictionServiceUnitTest {

    @InjectMocks
    IpRestrictionService ipRestrictionService;

    @Mock
    List<IpRestrictionStrategy> allValidations;

    @Mock
    CountryIpRestriction countryIpRestriction;

    @Mock
    IspIpRestriction ispIpRestriction;

    @BeforeEach
    public void setup() {
        doReturn(List.of(countryIpRestriction, ispIpRestriction).iterator())
                .when(allValidations).iterator();
    }

    @Test
    public void givenIpApiData_whenNoRestriction_thenNoException() {
        doReturn(IpRestrictionResult.allowed()).when(countryIpRestriction).validateIpAllowance(any());
        doReturn(IpRestrictionResult.allowed()).when(ispIpRestriction).validateIpAllowance(any());

        ipRestrictionService.validateIpInformation(IpApiData.builder().build());
    }

    @Test
    public void givenIpApiData_whenCountryRestriction_thenIpRestrictionException() {
        doReturn(IpRestrictionResult.denied("denied")).when(countryIpRestriction).validateIpAllowance(any());

        assertThrows(IpRestrictionException.class,
                () -> ipRestrictionService.validateIpInformation(IpApiData.builder().build()));
    }

    @Test
    public void givenIpApiData_whenIspRestriction_thenIpRestrictionException() {
        doReturn(IpRestrictionResult.allowed()).when(countryIpRestriction).validateIpAllowance(any());
        doReturn(IpRestrictionResult.denied("denied")).when(ispIpRestriction).validateIpAllowance(any());

        assertThrows(IpRestrictionException.class,
                () -> ipRestrictionService.validateIpInformation(IpApiData.builder().build()));
    }
}
