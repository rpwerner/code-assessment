package com.giftandgo.code.assessment.external.client.resttemplate.handler;

import com.giftandgo.code.assessment.external.client.resttemplate.exception.RestTemplateErrorException;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        throw new RestTemplateErrorException("Unexpected client error", url.getPath(), response.getStatusCode());
    }
}
