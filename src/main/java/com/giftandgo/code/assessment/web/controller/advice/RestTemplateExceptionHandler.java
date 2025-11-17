package com.giftandgo.code.assessment.web.controller.advice;

import com.giftandgo.code.assessment.external.client.resttemplate.RestTemplateErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(2)
public class RestTemplateExceptionHandler extends GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(RestTemplateExceptionHandler.class);

    @ExceptionHandler(RestTemplateErrorException.class)
    public ResponseEntity handleRestTemplateErrorException(RestTemplateErrorException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(buildSimpleErrorResponse(ex.buildErrorMessageWithPath()));
    }
}
