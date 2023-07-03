package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.services.GenresService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/genres")
public class GenresController {

    private final GenresService service;

    private GenresController(GenresService service) {
        this.service = service;
    }
}
