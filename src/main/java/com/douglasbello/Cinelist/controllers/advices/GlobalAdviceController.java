package com.douglasbello.Cinelist.controllers.advices;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RequestResponseDTO> handleNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RequestResponseDTO> handleInvalidEnumException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The type in one of the fields was not the right type."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorList.add(errorMessage);
        });
        return ResponseEntity.badRequest().body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), errorList.get(0)));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RequestResponseDTO> handleUniqueIndexException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(HttpStatus.CONFLICT.value(), "A field provided is unique and already in use."));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RequestResponseDTO> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You provided the wrong data type in the field: " + exception.getPropertyName()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RequestResponseDTO> handleAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponseDTO(HttpStatus.FORBIDDEN.value(), "Access denied."));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RequestResponseDTO> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RequestResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Authentication failed."));
    }
}
