package com.giftandgo.code.assessment.entity;

import lombok.Builder;

@Builder
public record User(String name, String transport, Double topSpeed) { }
