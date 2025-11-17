package com.giftandgo.code.assessment.web.mapper;

import com.giftandgo.code.assessment.web.controller.dto.UserResponseDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutcomeMapper {

    @Autowired
    Gson gson;

    public String transformUserResponseDTOsToJson(List<UserResponseDTO> userResponseDTOs) {
        return gson.toJson(userResponseDTOs);
    }
}
