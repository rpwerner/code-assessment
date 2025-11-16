package com.giftandgo.code.assessment.configuration;

import com.giftandgo.code.assessment.web.filter.strategy.CountryIpRestriction;
import com.giftandgo.code.assessment.web.filter.strategy.IpRestrictionStrategy;
import com.giftandgo.code.assessment.web.filter.strategy.IspIpRestriction;
import com.giftandgo.code.assessment.web.filter.strategy.NoIpRestriction;
import com.giftandgo.code.assessment.web.validation.service.DefaultUserValidationService;
import com.giftandgo.code.assessment.web.validation.service.NoUserValidationService;
import com.giftandgo.code.assessment.web.validation.service.UserValidationService;
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
