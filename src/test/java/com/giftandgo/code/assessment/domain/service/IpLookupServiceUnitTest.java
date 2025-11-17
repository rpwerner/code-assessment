package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.external.client.ipapi.IpAPIClient;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class IpLookupServiceUnitTest {

    @InjectMocks
    IpLookupService ipLookupService;

    @Mock
    IpAPIClient ipAPIClient;

    @Test
    public void givenIpAddress_whenNotFoundByClient_thenEmptyIpApiData() {
        doReturn(Optional.empty()).when(ipAPIClient).getIpInformationByIp(anyString());

        String ipAddress = "1.1.1.1";

        IpApiData ipApiData = ipLookupService.retrieveIpInformation(ipAddress);
        assertEquals(ipAddress, ipApiData.ip());
        assertNull(ipApiData.countryCode());
        assertNull(ipApiData.isp());
    }

    @Test
    public void givenIpAddress_whenFoundByClient_thenReturnCompleteIpApiData() {
        String ipAddress = "1.1.1.1";
        IpApiData expectedData = IpApiData.builder()
                .ip(ipAddress)
                .countryCode("GB")
                .isp("Virgin Media")
                .build();

        doReturn(Optional.of(expectedData)).when(ipAPIClient).getIpInformationByIp(anyString());

        IpApiData ipApiData = ipLookupService.retrieveIpInformation(ipAddress);
        assertEquals(expectedData.ip(), ipApiData.ip());
        assertEquals(expectedData.countryCode(), ipApiData.countryCode());
        assertEquals(expectedData.isp(), ipApiData.isp());
    }

}
