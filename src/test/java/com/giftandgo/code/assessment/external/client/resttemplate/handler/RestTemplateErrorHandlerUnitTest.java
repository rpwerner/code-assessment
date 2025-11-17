package com.giftandgo.code.assessment.external.client.resttemplate.handler;

import com.giftandgo.code.assessment.external.client.resttemplate.exception.RestTemplateErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RestTemplateErrorHandlerUnitTest {

    @Mock
    ClientHttpResponse clientHttpResponse;
    RestTemplateErrorHandler restTemplateErrorHandler = new RestTemplateErrorHandler();

    @Test
    public void givenClientHttpResponse_whenHas4xxHttpStatus_thenHasError() throws IOException {
        doReturn(HttpStatus.FORBIDDEN).when(clientHttpResponse).getStatusCode();
        assertTrue(restTemplateErrorHandler.hasError(clientHttpResponse));
    }

    @Test
    public void givenClientHttpResponse_whenHas5xxHttpStatus_thenHasError() throws IOException {
        doReturn(HttpStatus.INTERNAL_SERVER_ERROR).when(clientHttpResponse).getStatusCode();
        assertTrue(restTemplateErrorHandler.hasError(clientHttpResponse));
    }

    @Test
    public void givenClientResponse_whenHandleErrorCalled_thenThrowsRestTemplateErrorException() throws IOException {
        doReturn(HttpStatus.INTERNAL_SERVER_ERROR).when(clientHttpResponse).getStatusCode();
        assertThrows(RestTemplateErrorException.class,
                () -> restTemplateErrorHandler.handleError(URI.create("path"), HttpMethod.POST, clientHttpResponse));
    }

}
