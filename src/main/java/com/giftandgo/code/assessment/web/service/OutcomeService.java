package com.giftandgo.code.assessment.web.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequest;
import com.giftandgo.code.assessment.web.controller.dto.UserResponse;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class OutcomeService {

    private Gson gson;

    public byte[] generateOutcomeData(List<UserRequest> userRequests) {
        List<UserResponse> userResponses = userRequests.stream()
                .map(this::transformUserDTO)
                .toList();

        return gson.toJson(userResponses).getBytes(StandardCharsets.UTF_8);
    }

    protected UserResponse transformUserDTO(UserRequest userRequest) {
        return UserResponse.builder()
                .name(userRequest.name())
                .transport(userRequest.transport())
                .topSpeed(Double.parseDouble(userRequest.topSpeed()))
                .build();
    }
}
