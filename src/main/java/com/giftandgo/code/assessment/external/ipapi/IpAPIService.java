package com.giftandgo.code.assessment.external.ipapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpAPIService {

    @Autowired
    IpApiConfigurationProperties properties;

    @Autowired
    RestTemplate restTemplate;

    public IpApiData getIpInformationByIp(String ip) {

        ResponseEntity<IpApiData> responseEntity = restTemplate.getForEntity(properties.getUrl() + "/{ip}?fields=" + properties.getFields(),
                IpApiData.class, ip);

        return responseEntity.getBody();
    }

}
