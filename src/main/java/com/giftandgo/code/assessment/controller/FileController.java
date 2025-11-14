package com.giftandgo.code.assessment.controller;

import com.giftandgo.code.assessment.dto.UserDTO;
import com.giftandgo.code.assessment.request.ProcessUsersRequest;
import com.giftandgo.code.assessment.service.FileParsingService;
import com.giftandgo.code.assessment.service.OutcomeService;
import com.giftandgo.code.assessment.service.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/giftandgo")
public class FileController {
    @Autowired
    FileParsingService fileParsingService;

    @Autowired
    UserValidationService userValidationService;

    @Autowired
    OutcomeService outcomeService;

    @PostMapping(value = "/process/users")
    public ResponseEntity<byte[]> processUsers(@Valid @RequestBody ProcessUsersRequest processUsersRequest) {

        List<UserDTO> userDTOS = fileParsingService.parseAndReadUsers(processUsersRequest.usersInBytes());
        userValidationService.validateUsers(userDTOS);
        String outcomeJson = outcomeService.generateOutcomeData(userDTOS);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OutcomeFile.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(outcomeJson.getBytes(StandardCharsets.UTF_8));
    }

}