package com.giftandgo.code.assessment.web.controller.dto;

import com.giftandgo.code.assessment.domain.validation.annotation.CanBeDouble;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserRequest(
        @NotEmpty
        String uuid,
        @NotEmpty
        String id,
        @NotEmpty
        String name,
        @NotEmpty
        String likes,
        @NotEmpty
        String transport,
        @NotEmpty
        @CanBeDouble
        String averageSpeed,
        @NotEmpty
        @CanBeDouble
        String topSpeed) { }
