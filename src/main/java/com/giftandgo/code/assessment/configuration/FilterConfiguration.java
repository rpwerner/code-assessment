package com.giftandgo.code.assessment.configuration;

import com.giftandgo.code.assessment.domain.service.IpLookupService;
import com.giftandgo.code.assessment.domain.service.IpRestrictionService;
import com.giftandgo.code.assessment.domain.service.LoggingService;
import com.giftandgo.code.assessment.web.filter.IpRestrictionFilter;
import com.giftandgo.code.assessment.web.filter.LoggingDBFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class FilterConfiguration {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    @Autowired
    IpLookupService ipLookupService;

    @Autowired
    IpRestrictionService ipRestrictionService;

    @Autowired
    LoggingService loggingService;

    @Bean
    public FilterRegistrationBean<LoggingDBFilter> loggingFilter() {
        FilterRegistrationBean<LoggingDBFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoggingDBFilter(resolver, loggingService));
        registrationBean.addUrlPatterns("/giftandgo/*");
        registrationBean.setOrder(1); // should be first as it will control requests and responses for logging

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<IpRestrictionFilter> ipRestrictionFilter() {
        FilterRegistrationBean<IpRestrictionFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new IpRestrictionFilter(ipLookupService, ipRestrictionService));
        registrationBean.addUrlPatterns("/giftandgo/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
