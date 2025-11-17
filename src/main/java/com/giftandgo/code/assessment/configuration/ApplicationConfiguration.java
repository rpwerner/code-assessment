package com.giftandgo.code.assessment.configuration;

import com.giftandgo.code.assessment.domain.restriction.CountryIpRestriction;
import com.giftandgo.code.assessment.domain.restriction.IpRestrictionStrategy;
import com.giftandgo.code.assessment.domain.restriction.IspIpRestriction;
import com.giftandgo.code.assessment.domain.restriction.NoIpRestriction;
import com.giftandgo.code.assessment.domain.validation.service.DefaultUserValidationService;
import com.giftandgo.code.assessment.domain.validation.service.NoUserValidationService;
import com.giftandgo.code.assessment.domain.validation.service.UserValidationService;
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

    @Bean
    @ConditionalOnProperty(
            name = "giftandgo.feature.validation",
            havingValue = "true",
            matchIfMissing = true)
    public IpRestrictionStrategy countryIpRestriction() {
        return new CountryIpRestriction();
    }

    @Bean
    @ConditionalOnProperty(
            name = "giftandgo.feature.validation",
            havingValue = "true",
            matchIfMissing = true)
    public IpRestrictionStrategy ispIpRestriction() {
        return new IspIpRestriction();
    }

    @Bean
    @ConditionalOnProperty(
            name = "giftandgo.feature.validation",
            havingValue = "false")
    public IpRestrictionStrategy noIpRestriction() {
        return new NoIpRestriction();
    }
}
