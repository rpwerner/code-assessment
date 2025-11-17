package com.giftandgo.code.assessment.web.controller;

import com.giftandgo.code.assessment.domain.service.UserFileService;
import com.giftandgo.code.assessment.web.controller.dto.ProcessUsersRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/giftandgo")
@AllArgsConstructor
public class UserFileController {

    private UserFileService userFileService;

    @PostMapping(value = "/process/users")
    public ResponseEntity<byte[]> processUsers(@Valid @RequestBody ProcessUsersRequest processUsersRequest) {

        byte[] outcomeFileInBytes = userFileService.processUsers(processUsersRequest.usersInBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OutcomeFile.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(outcomeFileInBytes);
    }

}