package com.giftandgo.code.assessment.web.service;

import com.giftandgo.code.assessment.FileHandlingHelper;
import com.giftandgo.code.assessment.web.service.FileParsingService;
import com.giftandgo.code.assessment.web.exception.CannotProcessFileException;
import com.giftandgo.code.assessment.web.exception.InvalidFileDataStructureException;
import com.giftandgo.code.assessment.web.controller.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileParsingServiceUnitTest {

    FileHandlingHelper fileHandlingHelper = new FileHandlingHelper();

    @Spy
    FileParsingService fileParserService = new FileParsingService();

    @Test
    public void givenFileInBytes_whenFileExists_thenReturnListOfUsers() {
        File readFile = new File("test");
        doReturn(readFile).when(fileParserService).readFileFromBytes(any());
        List<UserRequestDTO> userRequestDTOS = new ArrayList<>();
        userRequestDTOS.add(UserRequestDTO.builder().build());
        doReturn(userRequestDTOS).when(fileParserService).processAndReadUsersFromFile(eq(readFile));

        List<UserRequestDTO> resultUserRequestDTOList = fileParserService.parseAndReadUsers(new byte[1]);

        assertEquals(userRequestDTOS.size(), resultUserRequestDTOList.size());
        assertEquals(userRequestDTOS.get(0), resultUserRequestDTOList.get(0));
        verify(fileParserService, times(1)).readFileFromBytes(any());
        verify(fileParserService, times(1)).processAndReadUsersFromFile(eq(readFile));
    }

    @Test
    public void givenFileInBytes_whenIsNotEmpty_thenFile() throws IOException {
        byte[] fileInBytes = fileHandlingHelper.getFileInBytes("files/EntryFile.txt");

        File fileRead = fileParserService.readFileFromBytes(fileInBytes);
        List<String> allLines = Files.readAllLines(Paths.get(fileRead.toURI()));

        assertEquals(3, allLines.size());
    }

    @Test
    public void givenFile_whenContentIsCorrect_thenReturnListOfUsers() {
        doReturn(UserRequestDTO.builder().build()).when(fileParserService).parseUserFromFile(anyString());

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("files/EntryFile.txt").getFile());

        List<UserRequestDTO> userRequestDTOS = fileParserService.processAndReadUsersFromFile(file);
        assertFalse(userRequestDTOS.isEmpty());
        assertEquals(3, userRequestDTOS.size());
    }

    @Test
    public void givenFile_whenFilePathIsIncorrect_thenCannotProcessFileException() {
        assertThrows(CannotProcessFileException.class,
                () -> fileParserService.processAndReadUsersFromFile(new File("")));
    }

    @Test
    public void givenLineFromFile_whenUserIsValid_thenReturnCorrectUser() {
        String line = "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1";
        UserRequestDTO userRequestDTO = fileParserService.parseUserFromFile(line);

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
        assertThrows(InvalidFileDataStructureException.class, () -> fileParserService.parseUserFromFile(line));
    }
}
