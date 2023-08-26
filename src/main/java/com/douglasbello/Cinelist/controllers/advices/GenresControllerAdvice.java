package com.douglasbello.Cinelist.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.douglasbello.Cinelist.controllers.GenresController;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;

@RestControllerAdvice(assignableTypes = {GenresController.class})
public class GenresControllerAdvice {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RequestResponseDTO> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Genres not found by the provided name."));
    }
}
