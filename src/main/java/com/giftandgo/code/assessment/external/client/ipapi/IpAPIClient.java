package com.giftandgo.code.assessment.external.client.ipapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class IpAPIClient {

    @Autowired
    IpApiConfigurationProperties properties;

    @Autowired
    RestTemplate restTemplate;

    public Optional<IpApiData> getIpInformationByIp(String ip) {

        ResponseEntity<IpApiData> responseEntity = restTemplate.getForEntity(properties.getUrl() + "/{ip}?fields=" + properties.getFields(),
                IpApiData.class, ip);

        return Optional.of(responseEntity.getBody());
    }

}
