package com.giftandgo.code.assessment.controller;

import com.giftandgo.code.assessment.exception.CannotProcessFileException;
import com.giftandgo.code.assessment.exception.InvalidFileDataStructureException;
import com.giftandgo.code.assessment.exception.UserValidationException;
import com.giftandgo.code.assessment.response.ErrorReponse;
import jakarta.validation.ConstraintViolation;
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
public class FileControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CannotProcessFileException.class)
    public ResponseEntity handleCannotProcessFileException(CannotProcessFileException ex) {
        return ResponseEntity.badRequest().body(buildSimpleErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidFileDataStructureException.class)
    public ResponseEntity handleIncompleteUserDataException(InvalidFileDataStructureException ex) {
        return ResponseEntity.badRequest().body(buildSimpleErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity handleUserValidationException(UserValidationException ex) {
        List<String> errors = ex.getViolations()
                .stream()
                .map(violation -> "Field: ["+violation.getPropertyPath()+"]: "+violation.getMessage())
                .toList();
        return ResponseEntity.badRequest().body(new ErrorReponse(errors));
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
    public ResponseEntity handleAll(RuntimeException ex) {
        //log exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildSimpleErrorResponse("Unexpected server error"));
    }

    protected ErrorReponse buildSimpleErrorResponse(String errorMessage) {
        List<String> errors = new ArrayList<>();
        errors.add(errorMessage);
        return new ErrorReponse(errors);
    }
}
