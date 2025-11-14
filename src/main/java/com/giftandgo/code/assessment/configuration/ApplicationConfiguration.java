package com.giftandgo.code.assessment.configuration;

import com.giftandgo.code.assessment.service.DefaultUserValidationService;
import com.giftandgo.code.assessment.service.NoUserValidationService;
import com.giftandgo.code.assessment.service.UserValidationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "giftandgo.feature.validation",
            havingValue = "true",
            matchIfMissing = true)
    public UserValidationService defaultUserValidation() {
        return new DefaultUserValidationService();
    }

    @Bean
    @ConditionalOnProperty(
            name = "giftandgo.feature.validation",
            havingValue = "false")
    public UserValidationService noUserValidation() {
        return new NoUserValidationService();
    }
}
