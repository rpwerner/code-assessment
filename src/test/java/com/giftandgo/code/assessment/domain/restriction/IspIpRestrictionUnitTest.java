package com.giftandgo.code.assessment.domain.restriction;

import com.giftandgo.code.assessment.configuration.ISPsBlacklistProperties;
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
public class IspIpRestrictionUnitTest {

    @InjectMocks
    IspIpRestriction ispIpRestriction;

    @Mock
    ISPsBlacklistProperties blackListIsps;

    @Test
    public void givenEmptyIpApiData_whenIspIsNull_thenIpRestrictionAllowed() {
        assertTrue(ispIpRestriction.validateIpAllowance(IpApiData.builder().build()).isAllowed());
    }

    @Test
    public void givenIpApiData_whenIspIsBlacklisted_thenIpRestrictionNotAllowed() {
        Set<String> blacklist = Set.of("AWS");
        doReturn(blacklist).when(blackListIsps).getIsps();

        IpRestrictionResult ispRestriction = ispIpRestriction
                .validateIpAllowance(IpApiData
                        .builder()
                        .isp("AWS")
                        .build());

        assertFalse(ispRestriction.isAllowed());
        assertTrue(ispRestriction.getReason().contains("AWS"));
    }

    @Test
    public void givenIpApiData_whenIspIsNotBlacklisted_thenIpRestrictionAllowed() {
        Set<String> blacklist = Set.of("AWS");
        doReturn(blacklist).when(blackListIsps).getIsps();

        IpRestrictionResult ispRestriction = ispIpRestriction
                .validateIpAllowance(IpApiData
                        .builder()
                        .isp("Virgin Media")
                        .build());

        assertTrue(ispRestriction.isAllowed());
    }

}
