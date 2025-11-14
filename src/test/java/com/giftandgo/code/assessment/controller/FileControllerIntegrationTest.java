package com.giftandgo.code.assessment.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.code.assessment.entity.User;
import com.giftandgo.code.assessment.response.ErrorReponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    public void givenValidFile_whenTheDataIsCorrect_thenOkRequestWithParsedUsers(int userPosition, User expectedUser) throws Exception {
        String inputFileInBase64 = Base64.getEncoder().encodeToString(getFileInBytes("files/EntryFile.txt"));

        MvcResult mvcResult = mockMvc.perform(
                        post("/giftandgo/process/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"usersInBytes\":\"" + inputFileInBase64 + "\"}")
                )
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = objectMapper.readValue(json, new TypeReference<>() { });

        assertFalse(users.isEmpty());
        assertEquals(3, users.size());

        User currentResponseUser = users.get(userPosition);
        assertEquals(expectedUser.name(), currentResponseUser.name());
        assertEquals(expectedUser.transport(), currentResponseUser.transport());
        assertEquals(expectedUser.topSpeed(), currentResponseUser.topSpeed());
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(0,
                        User.builder()
                                .name("John Smith")
                                .transport("Rides A Bike")
                                .topSpeed(12.1)
                                .build()
                ),
                Arguments.of(1,
                        User.builder()
                                .name("Mike Smith")
                                .transport("Drives an SUV")
                                .topSpeed(95.5)
                                .build()
                ),
                Arguments.of(2,
                        User.builder()
                                .name("Jenny Walters")
                                .transport("Rides A Scooter")
                                .topSpeed(15.3)
                                .build()
                )
        );
    }

    @Test
    public void givenValidFile_whenContentIsEmpty_thenBadRequest() throws Exception {
        String inputFileInBase64 = Base64.getEncoder().encodeToString(getFileInBytes("files/EmptyEntryFile.txt"));

        MvcResult mvcResult = mockMvc.perform(
                        post("/giftandgo/process/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"usersInBytes\":\"" + inputFileInBase64 + "\"}")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorReponse errorReponse = objectMapper.readValue(json, ErrorReponse.class);

        List<String> errors = errorReponse.errors();
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("Input request file cannot be empty", errors.get(0));
    }

    @Test
    public void givenValidFile_whenUserHasMissingField_thenBadRequest() throws Exception {
        String inputFileInBase64 = Base64.getEncoder().encodeToString(getFileInBytes("files/EntryFile_WithMissingData.txt"));

        MvcResult mvcResult = mockMvc.perform(
                        post("/giftandgo/process/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"usersInBytes\":\"" + inputFileInBase64 + "\"}")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorReponse errorReponse = objectMapper.readValue(json, ErrorReponse.class);

        List<String> errors = errorReponse.errors();
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
    }

    private byte[] getFileInBytes(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        return inputStream.readAllBytes();
    }

}
