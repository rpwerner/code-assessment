package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
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

        UserRequestDTO userRequestDTO = UserRequestDTO.builder().build();
        doReturn(userRequestDTO).when(fileParserService).readUserFromFile(anyString());

        List<UserRequestDTO> resultUserRequestDTOList = fileParserService.parseAndReadUsers(new byte[1]);

        assertEquals(1, resultUserRequestDTOList.size());
        assertEquals(userRequestDTO, resultUserRequestDTOList.get(0));
    }

    @Test
    public void givenLineFromFile_whenUserIsValid_thenReturnCorrectUser() {
        String line = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1";
        UserRequestDTO userRequestDTO = fileParserService.readUserFromFile(line);

        assertEquals("18148426-89e1-11ee-b9d1-0242ac120002", userRequestDTO.uuid());
        assertEquals("1X1D14", userRequestDTO.id());
        assertEquals("John Smith", userRequestDTO.name());
        assertEquals("Likes Apricots", userRequestDTO.likes());
        assertEquals("Rides A Bike", userRequestDTO.transport());
        assertEquals("6.2", userRequestDTO.averageSpeed());
        assertEquals("12.1", userRequestDTO.topSpeed());
    }

    @Test
    public void givenLineFromFile_whenUserHasMissingData_thenThrowInvalidFileDataStructureException() {
        String line = "18148426-89e1-11ee-b9d1-0242ac120002|John Smith|Likes Apricots|Rides A Bike|6.2|12.1 ";
        assertThrows(InvalidFileDataStructureException.class, () -> fileParserService.readUserFromFile(line));
    }
}
