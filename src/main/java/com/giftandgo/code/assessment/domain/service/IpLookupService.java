package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.external.client.ipapi.IpAPIClient;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IpLookupService {

    private IpAPIClient ipAPIClient;

    public IpApiData retrieveIpInformation(String ipAddress) {
        return ipAPIClient.getIpInformationByIp(ipAddress)
                .orElse(IpApiData
                        .builder()
                        .ip(ipAddress)
                        .build());
    }
}
