package com.giftandgo.code.assessment.external.client.resttemplate;

import com.giftandgo.code.assessment.external.client.resttemplate.handler.RestTemplateErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }
}
