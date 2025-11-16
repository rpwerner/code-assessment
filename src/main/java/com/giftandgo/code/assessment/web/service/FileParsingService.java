package com.giftandgo.code.assessment.web.service;

import com.giftandgo.code.assessment.web.exception.CannotProcessFileException;
import com.giftandgo.code.assessment.web.exception.InvalidFileDataStructureException;
import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileParsingService {

    public List<UserRequestDTO> parseAndReadUsers(byte[] usersInBytes) {
        File usersFile = readFileFromBytes(usersInBytes);
        return processAndReadUsersFromFile(usersFile);
    }

    protected File readFileFromBytes(byte[] usersInBytes) {
        File file = new File("EntryFile.txt");
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(usersInBytes);
            os.close();
        } catch (IOException e) {
            throw new CannotProcessFileException(e.getMessage(), e);
        }

        return file;
    }

    protected List<UserRequestDTO> processAndReadUsersFromFile(File requestFile) {
        ArrayList<UserRequestDTO> userRequestDTOS = new ArrayList<>();
        try {
            List<String> allLines = Files.readAllLines(Paths.get(requestFile.toURI()));

            for (String line : allLines) {
                userRequestDTOS.add(parseUserFromFile(line));
            }
        } catch (IOException e) {
            throw new CannotProcessFileException("Error while reading file", e);
        }

        requestFile.delete();
        return userRequestDTOS;
    }

    protected UserRequestDTO parseUserFromFile(String line) {
        String[] userFromLine = line.split("\\|");

        if(userFromLine.length == 7) {
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

        throw new InvalidFileDataStructureException("Incomplete user data. Expected format is UUID|ID|Name|LIKES|TRANSPORT|AVG_SPEED|TOP_SPEED. Received: "+line);
    }
}
