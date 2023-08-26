package com.douglasbello.Cinelist.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.douglasbello.Cinelist.controllers.MovieController;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundWithNameException;

@RestControllerAdvice(assignableTypes = {MovieController.class})
public class MovieControllerAdvice {

    @ExceptionHandler(ResourceNotFoundWithNameException.class)
    public ResponseEntity<RequestResponseDTO> handleNullPointerException(ResourceNotFoundWithNameException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    } 
}
