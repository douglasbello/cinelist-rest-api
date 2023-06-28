package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.services.TVShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tvshows")
public class TVShowController {
    private final TVShowService service;

    public TVShowController(TVShowService service) {
	    this.service = service;
    }
}
