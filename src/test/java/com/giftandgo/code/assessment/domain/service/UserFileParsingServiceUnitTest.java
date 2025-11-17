package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequest;
import com.giftandgo.code.assessment.web.exception.InvalidFileDataStructureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserFileParsingServiceUnitTest {

    @Spy
    UserFileParsingService fileParserService = new UserFileParsingService();

    @Test
    public void givenFileInBytes_whenFileExists_thenReturnListOfUsers() {

        UserRequest userRequest = UserRequest.builder().build();
        doReturn(userRequest).when(fileParserService).readUserFromFile(anyString());

        List<UserRequest> resultUserRequestList = fileParserService.parseAndReadUsers(new byte[1]);

        assertEquals(1, resultUserRequestList.size());
        assertEquals(userRequest, resultUserRequestList.get(0));
    }

    @Test
    public void givenLineFromFile_whenUserIsValid_thenReturnCorrectUser() {
        String line = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1";
        UserRequest userRequest = fileParserService.readUserFromFile(line);

        assertEquals("18148426-89e1-11ee-b9d1-0242ac120002", userRequest.uuid());
        assertEquals("1X1D14", userRequest.id());
        assertEquals("John Smith", userRequest.name());
        assertEquals("Likes Apricots", userRequest.likes());
        assertEquals("Rides A Bike", userRequest.transport());
        assertEquals("6.2", userRequest.averageSpeed());
        assertEquals("12.1", userRequest.topSpeed());
    }

    @Test
    public void givenLineFromFile_whenUserHasMissingData_thenThrowInvalidFileDataStructureException() {
        String line = "18148426-89e1-11ee-b9d1-0242ac120002|John Smith|Likes Apricots|Rides A Bike|6.2|12.1 ";
        assertThrows(InvalidFileDataStructureException.class, () -> fileParserService.readUserFromFile(line));
    }
}
