package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dto.GenresDTO;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.services.GenresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/genres")
public class GenresController {

    private final GenresService service;

    private GenresController(GenresService service) {
        this.service = service;
    }
}
