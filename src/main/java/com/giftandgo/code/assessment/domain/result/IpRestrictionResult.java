package com.giftandgo.code.assessment.domain.result;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IpRestrictionResult {

    boolean allowed;
    String reason;

    public static IpRestrictionResult allowed() {
        return IpRestrictionResult.builder()
                .allowed(true)
                .build();
    }

    public static IpRestrictionResult denied(String reason) {
        return IpRestrictionResult.builder()
                .allowed(false)
                .reason(reason)
                .build();
    }
}
