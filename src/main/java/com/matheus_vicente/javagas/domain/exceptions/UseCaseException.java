package com.matheus_vicente.javagas.domain.exceptions;

public class UseCaseException extends RuntimeException {
    public UseCaseException(String message) {
        super(message);
    }
}
