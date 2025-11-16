package com.giftandgo.code.assessment.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "giftandgo.blacklist")
public class ISPsBlacklistProperties {
    private Set<String> isps;
}
