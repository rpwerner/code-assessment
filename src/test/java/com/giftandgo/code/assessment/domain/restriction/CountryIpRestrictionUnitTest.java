package com.giftandgo.code.assessment.domain.restriction;

import com.giftandgo.code.assessment.configuration.CountriesBlacklistProperties;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CountryIpRestrictionUnitTest {

    @InjectMocks
    CountryIpRestriction countryIpRestriction;

    @Mock
    CountriesBlacklistProperties blackListCountries;

    @Test
    public void givenEmptyIpApiData_whenCountryCodeIsNull_thenIpRestrictionAllowed() {
        assertTrue(countryIpRestriction.validateIpAllowance(IpApiData.builder().build()).isAllowed());
    }

    @Test
    public void givenIpApiData_whenCountryCodeIsBlacklisted_thenIpRestrictionNotAllowed() {
        Set<String> blacklist = Set.of("US");
        doReturn(blacklist).when(blackListCountries).getCountries();

        IpRestrictionResult countryRestriction = countryIpRestriction
                .validateIpAllowance(IpApiData
                        .builder()
                        .countryCode("US")
                        .build());

        assertFalse(countryRestriction.isAllowed());
        assertTrue(countryRestriction.getReason().contains("US"));
    }

    @Test
    public void givenIpApiData_whenCountryCodeIsNotBlacklisted_thenIpRestrictionAllowed() {
        Set<String> blacklist = Set.of("US");
        doReturn(blacklist).when(blackListCountries).getCountries();

        IpRestrictionResult countryRestriction = countryIpRestriction
                .validateIpAllowance(IpApiData
                        .builder()
                        .countryCode("BR")
                        .build());

        assertTrue(countryRestriction.isAllowed());
    }

}
