package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.external.client.ipapi.IpAPIClient;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpLookupService {

    @Autowired
    IpAPIClient ipAPIClient;

    public IpApiData retrieveIpInformation(String ipAddress) {
        return ipAPIClient.getIpInformationByIp(ipAddress)
                .orElse(IpApiData
                        .builder()
                        .ip(ipAddress)
                        .build());
    }
}
