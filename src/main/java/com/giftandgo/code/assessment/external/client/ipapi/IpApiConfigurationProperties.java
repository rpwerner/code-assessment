package com.giftandgo.code.assessment.external.client.ipapi;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ipapi")
@Data
public class IpApiConfigurationProperties {
    @NotBlank
    private String url;
    @NotBlank
    private String fields;
}
