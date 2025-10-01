package com.eira.guilherme.technical_assistance.entrypoint.controller;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.commom.exception.ResourceNotFoundException;
import com.eira.guilherme.technical_assistance.entrypoint.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleError400(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var errors = ex.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, f -> Objects.requireNonNull(f.getDefaultMessage())));
        var error = new ErrorResponseDTO(
                "Erro de formatação",
                "É necessário que os campos estejam preenchidos conforme indicado em 'invalidFields'",
                req.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now().toString(),
                errors
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleError422(BusinessException ex, HttpServletRequest req) {
        var error =  new ErrorResponseDTO(
                "Erro de negócio",
                ex.getMessage(),
                req.getRequestURI(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                LocalDateTime.now().toString(),
                null
        );
        return ResponseEntity.unprocessableEntity().body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleError404(ResourceNotFoundException ex, HttpServletRequest req) {
        var error = new ErrorResponseDTO(
                "Erro de busca",
                ex.getMessage(),
                req.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().toString(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleError500(Exception ex, HttpServletRequest req) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "Erro Interno de Servidor",
                ex.getMessage(),
                req.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now().toString(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
}
