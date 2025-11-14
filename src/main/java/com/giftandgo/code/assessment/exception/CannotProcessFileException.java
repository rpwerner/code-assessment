package com.giftandgo.code.assessment.exception;

public class CannotProcessFileException extends RuntimeException {
    public CannotProcessFileException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
