package com.giftandgo.code.assessment.web.controller.advice;

import com.giftandgo.code.assessment.web.controller.dto.ErrorReponse;
import com.giftandgo.code.assessment.web.exception.CannotProcessFileException;
import com.giftandgo.code.assessment.web.exception.InvalidFileDataStructureException;
import com.giftandgo.code.assessment.web.exception.UserValidationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class FileExceptionHandler extends GlobalExceptionHandler {

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
        return ResponseEntity.badRequest().body(new ErrorReponse(ex.getViolations()));
    }
}
