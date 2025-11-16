package com.giftandgo.code.assessment.web.controller.dto;

import lombok.Builder;

@Builder
public record UserResponseDTO(String name, String transport, Double topSpeed) { }
