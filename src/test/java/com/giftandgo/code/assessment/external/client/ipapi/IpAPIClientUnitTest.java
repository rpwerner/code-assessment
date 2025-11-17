package com.giftandgo.code.assessment.external.client.ipapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class IpAPIClientUnitTest {

    @InjectMocks
    IpAPIClient ipAPIClient;

    @Mock
    IpApiConfigurationProperties properties;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void givenIpAddress_whenIsNotEmpty_thenResponseEntityOk() {
        IpApiData expectedResponse = IpApiData.builder()
                .countryCode("code")
                .isp("isp")
                .ip("IP")
                .build();
        doReturn(ResponseEntity.ok(expectedResponse)).when(restTemplate).getForEntity(anyString(), eq(IpApiData.class), anyString());

        Optional<IpApiData> ipApiDataOptional = ipAPIClient.getIpInformationByIp(expectedResponse.ip());

        assertFalse(ipApiDataOptional.isEmpty());
        IpApiData ipApiDataResult = ipApiDataOptional.get();
        assertEquals(expectedResponse.isp(), ipApiDataResult.isp());
        assertEquals(expectedResponse.ip(), ipApiDataResult.ip());
        assertEquals(expectedResponse.countryCode(), ipApiDataResult.countryCode());

    }

}
