package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import com.giftandgo.code.assessment.web.exception.InvalidFileDataStructureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFileParsingService {

    public List<UserRequestDTO> parseAndReadUsers(byte[] usersInBytes) {
        return new String(usersInBytes)
                .lines()
                .map(this::readUserFromFile)
                .toList();
    }

    protected UserRequestDTO readUserFromFile(String line) {
        String[] userFromLine = line.split("\\|");

        if (userFromLine.length == 7) {
            //all data for User is present
            return UserRequestDTO.builder()
                    .uuid(userFromLine[0])
                    .id(userFromLine[1])
                    .name(userFromLine[2])
                    .likes(userFromLine[3])
                    .transport(userFromLine[4])
                    .averageSpeed(userFromLine[5])
                    .topSpeed(userFromLine[6])
                    .build();
        }

        throw new InvalidFileDataStructureException("Incomplete user data. Expected format is UUID|ID|Name|LIKES|TRANSPORT|AVG_SPEED|TOP_SPEED. Received: " + line);
    }
}
