package com.giftandgo.code.assessment.external.client.ipapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record IpApiData(@JsonProperty("query") String ip, String countryCode, String isp) { }
