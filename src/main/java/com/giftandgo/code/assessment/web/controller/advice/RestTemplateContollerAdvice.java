package com.giftandgo.code.assessment.web.controller.advice;

import com.giftandgo.code.assessment.external.client.resttemplate.exception.RestTemplateErrorException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(2)
public class RestTemplateContollerAdvice extends ApplicationControllerAdvice {

    @ExceptionHandler(RestTemplateErrorException.class)
    public ResponseEntity handleRestTemplateErrorException(RestTemplateErrorException ex) {

        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(buildSimpleErrorResponse(ex.buildErrorMessageWithPath()));
    }
}
