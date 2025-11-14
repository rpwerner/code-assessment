package com.giftandgo.code.assessment.service;

import com.giftandgo.code.assessment.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserValidationService {
    void validateUsers(List<UserDTO> userDTOS);
}
