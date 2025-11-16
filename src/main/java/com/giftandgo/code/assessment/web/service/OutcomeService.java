package com.giftandgo.code.assessment.web.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import com.giftandgo.code.assessment.web.controller.dto.UserResponseDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutcomeService {

    @Autowired
    Gson gson;

    public String generateOutcomeData(List<UserRequestDTO> userRequestDTOS) {
        List<UserResponseDTO> userResponseDTOS = userRequestDTOS.stream()
                .map(dto -> UserResponseDTO.builder()
                        .name(dto.name())
                        .transport(dto.transport())
                        .topSpeed(Double.parseDouble(dto.topSpeed()))
                        .build())
                .toList();

        return gson.toJson(userResponseDTOS);
    }
}
