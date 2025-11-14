package com.giftandgo.code.assessment.service;

import com.giftandgo.code.assessment.dto.UserDTO;
import com.giftandgo.code.assessment.entity.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutcomeService {

    @Autowired
    Gson gson;

    public String generateOutcomeData(List<UserDTO> userDTOs) {
        List<User> users = userDTOs.stream()
                .map(dto -> User.builder()
                        .name(dto.name())
                        .transport(dto.transport())
                        .topSpeed(Double.parseDouble(dto.topSpeed()))
                        .build())
                .toList();

        return gson.toJson(users);
    }
}
