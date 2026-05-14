package com.matheus_vicente.javagas.infra.http.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.matheus_vicente.javagas.application.dtos.ErroResponseDTO;
import com.matheus_vicente.javagas.domain.exceptions.DomainException;
import com.matheus_vicente.javagas.domain.exceptions.EntidadeNaoEncontradaException;
import com.matheus_vicente.javagas.domain.exceptions.UseCaseException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErroResponseDTO> handleDomainException(DomainException ex, HttpServletRequest request) {
        ErroResponseDTO erro = new ErroResponseDTO(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(UseCaseException.class)
    public ResponseEntity<ErroResponseDTO> handleUseCaseException(UseCaseException ex, HttpServletRequest request) {
        ErroResponseDTO erro = new ErroResponseDTO(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroResponseDTO> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        ErroResponseDTO erro = new ErroResponseDTO(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        ErroResponseDTO erro = new ErroResponseDTO(
            "Ocorreu um erro interno inesperado",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            request.getRequestURI()
        );

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
