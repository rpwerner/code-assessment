package com.giftandgo.code.assessment.web.mapper;

import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import com.giftandgo.code.assessment.web.controller.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserResponseDTO transformUserDTO(UserRequestDTO userRequestDTO) {
        return UserResponseDTO.builder()
                .name(userRequestDTO.name())
                .transport(userRequestDTO.transport())
                .topSpeed(Double.parseDouble(userRequestDTO.topSpeed()))
                .build();
    }
}
