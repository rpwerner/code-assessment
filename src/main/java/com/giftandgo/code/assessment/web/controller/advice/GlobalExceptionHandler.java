package com.giftandgo.code.assessment.web.controller.advice;

import com.giftandgo.code.assessment.web.exception.IpRestrictionException;
import com.giftandgo.code.assessment.web.controller.dto.ErrorReponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IpRestrictionException.class)
    public ResponseEntity handleIpRestrictionException(IpRestrictionException ex) {
        //TODO maybe tell if it is the IP or ISP the problem?
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildSimpleErrorResponse(ex.getMessage()));
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers,
                                                          HttpStatusCode status,
                                                          WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().body(new ErrorReponse(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAll(Exception ex) {
        //log exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildSimpleErrorResponse("Unexpected server error"));
    }

    protected ErrorReponse buildSimpleErrorResponse(String errorMessage) {
        List<String> errors = new ArrayList<>();
        errors.add(errorMessage);
        return new ErrorReponse(errors);
    }
}
