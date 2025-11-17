package com.giftandgo.code.assessment.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giftandgo.code.assessment.FileHandlingHelper;
import com.giftandgo.code.assessment.WiremockStubHelper;
import com.giftandgo.code.assessment.domain.entity.LogEntry;
import com.giftandgo.code.assessment.domain.persistence.LogEntryRepository;
import com.giftandgo.code.assessment.web.controller.dto.UserResponseDTO;
import com.giftandgo.code.assessment.web.controller.dto.ErrorReponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.wiremock.spring.EnableWireMock;

import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWireMock
@TestPropertySource(properties = {
        "ipapi.url=http://localhost:${wiremock.server.port}/json",
        "ipapi.fields=query,countryCode,isp" //TODO maybe this can be removed
})
public class UserFileControllerIntegrationTest {

    FileHandlingHelper fileHandlingHelper = new FileHandlingHelper();
    WiremockStubHelper wiremockStubHelper = new WiremockStubHelper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LogEntryRepository logEntryRepository;

    @ParameterizedTest
    @MethodSource("generateExpectedUsers")
    public void givenValidFile_whenTheDataIsCorrect_thenOkRequestWithParsedUsers(int userPosition, UserResponseDTO expectedUserResponseDTO) throws Exception {
        String givenIp = "123.123.123.123";
        wiremockStubHelper.stubIpNotBlackListed(givenIp);

        String inputFileInBase64 = Base64.getEncoder().encodeToString(fileHandlingHelper.getFileInBytes("files/EntryFile.txt"));

        MvcResult mvcResult = mockMvc.perform(
                        post("/giftandgo/process/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"usersInBytes\":\"" + inputFileInBase64 + "\"}")
                )
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserResponseDTO> userResponseDTOS = objectMapper.readValue(json, new TypeReference<>() {});

        assertFalse(userResponseDTOS.isEmpty());
        assertEquals(3, userResponseDTOS.size());

        UserResponseDTO currentResponseUserResponseDTO = userResponseDTOS.get(userPosition);
        assertEquals(expectedUserResponseDTO.name(), currentResponseUserResponseDTO.name());
        assertEquals(expectedUserResponseDTO.transport(), currentResponseUserResponseDTO.transport());
        assertEquals(expectedUserResponseDTO.topSpeed(), currentResponseUserResponseDTO.topSpeed());
    }

    private static Stream<Arguments> generateExpectedUsers() {
        return Stream.of(
                Arguments.of(0,
                        UserResponseDTO.builder().name("John Smith").transport("Rides A Bike").topSpeed(12.1).build()),
                Arguments.of(1,
                        UserResponseDTO.builder().name("Mike Smith").transport("Drives an SUV").topSpeed(95.5).build()),
                Arguments.of(2,
                        UserResponseDTO.builder().name("Jenny Walters").transport("Rides A Scooter").topSpeed(15.3).build()));
    }

    @Test
    public void givenValidFile_whenContentIsEmpty_thenBadRequest() throws Exception {
        String givenIp = "123.123.123.123";
        wiremockStubHelper.stubIpNotBlackListed(givenIp);
        String inputFileInBase64 = Base64.getEncoder().encodeToString(fileHandlingHelper.getFileInBytes("files/EmptyEntryFile.txt"));

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
        String givenIp = "123.123.123.123";
        wiremockStubHelper.stubIpNotBlackListed(givenIp);
        String inputFileInBase64 = Base64.getEncoder().encodeToString(fileHandlingHelper.getFileInBytes("files/EntryFile_WithMissingData.txt"));

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

    @ParameterizedTest
    @MethodSource("generateBlackListedCountryCodes")
    public void givenValidFile_whenCountryIpIsBlackListed_thenForbiddenRequest(String countryCode) throws Exception {
        String givenIp = "123.123.123.123";
        wiremockStubHelper.stubIpIsCountryBlackListed(countryCode, givenIp);

        String inputFileInBase64 = Base64.getEncoder().encodeToString(fileHandlingHelper.getFileInBytes("files/EntryFile.txt"));

        mockMvc.perform(
                        post("/giftandgo/process/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"usersInBytes\":\"" + inputFileInBase64 + "\"}")
                )
                .andExpect(status().isForbidden());
    }

    private static Stream<Arguments> generateBlackListedCountryCodes() {
        return Stream.of(
                Arguments.of("US"),
                Arguments.of("CN"),
                Arguments.of("ES"));
    }

    @ParameterizedTest
    @MethodSource("generateBlackListedISPs")
    public void givenValidFile_whenISPIsBlackListed_thenForbiddenRequest(String ispName) throws Exception {
        String givenIp = "123.123.123.123";
        wiremockStubHelper.stubIpIsISPBlackListed(ispName, givenIp);

        String inputFileInBase64 = Base64.getEncoder().encodeToString(fileHandlingHelper.getFileInBytes("files/EntryFile.txt"));

        mockMvc.perform(
                        post("/giftandgo/process/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"usersInBytes\":\"" + inputFileInBase64 + "\"}")
                )
                .andExpect(status().isForbidden());

    }

    private static Stream<Arguments> generateBlackListedISPs() {
        return Stream.of(
                Arguments.of("Amazon.com, Inc."),
                Arguments.of("Google LLC"),
                Arguments.of("Microsoft Corporation"));
    }

    @Test
    public void givenValidFile_whenCountryIpIsBlackListed_thenForbiddenRequestAndFileLoggingIsSaved() throws Exception {
        String givenIp = "1.1.1.1";
        String isp = "Virgin Media";
        String countryCode = "US";
        wiremockStubHelper.stubIpIsCountryBlackListed(countryCode, givenIp);

        String inputFileInBase64 = Base64.getEncoder().encodeToString(fileHandlingHelper.getFileInBytes("files/EntryFile.txt"));
        mockMvc.perform(
                        post("/giftandgo/process/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"usersInBytes\":\"" + inputFileInBase64 + "\"}")
                )
                .andExpect(status().isForbidden());

        List<LogEntry> byIp = logEntryRepository.findByIpAddress(givenIp);
        assertFalse(byIp.isEmpty());
        assertEquals(1, byIp.size());

        LogEntry logEntry = byIp.get(0);
        assertEquals(givenIp, logEntry.getIpAddress());
        assertEquals("/giftandgo/process/users", logEntry.getUri());
        assertEquals(HttpStatus.FORBIDDEN.value(), logEntry.getResponseCode());
        assertEquals(countryCode, logEntry.getIpCountry());
        assertEquals(isp, logEntry.getIpProvider());
    }
}
