package com.giftandgo.code.assessment.external.client.resttemplate;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RestTemplateErrorException extends RuntimeException {
    String path;
    HttpStatusCode httpStatusCode;

    public RestTemplateErrorException(String message, String path, HttpStatusCode statusCode) {
        super(message);
        this.path = path;
        this.httpStatusCode = statusCode;
    }

    public String buildErrorMessageWithPath() {
        return getMessage()+" (url: "+path+")";
    }
}
