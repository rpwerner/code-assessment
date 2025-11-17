package com.giftandgo.code.assessment.web.service;

import com.giftandgo.code.assessment.web.mapper.OutcomeMapper;
import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import com.giftandgo.code.assessment.web.controller.dto.UserResponseDTO;
import com.giftandgo.code.assessment.web.mapper.UserDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class OutcomeService {

    @Autowired
    UserDTOMapper userDTOMapper;

    @Autowired
    OutcomeMapper outcomeMapper;

    public byte[] generateOutcomeData(List<UserRequestDTO> userRequestDTOS) {
        List<UserResponseDTO> userResponseDTOS = userRequestDTOS.stream()
                .map(dto -> userDTOMapper.transformUserDTO(dto))
                .toList();

        return outcomeMapper.transformUserResponseDTOsToJson(userResponseDTOS)
                .getBytes(StandardCharsets.UTF_8);
    }
}
