package com.giftandgo.code.assessment.request;

import jakarta.validation.constraints.NotEmpty;

public record ProcessUsersRequest (
        @NotEmpty(message = "Input request file cannot be empty")
        byte[] usersInBytes
) {

}
