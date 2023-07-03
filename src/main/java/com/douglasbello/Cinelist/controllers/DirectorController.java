package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.services.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public List<Director> getAll() {
        return directorService.getAll();
    }

    @PostMapping
    public Director insert(@RequestBody Director obj) {
        return directorService.insert(obj);
    }

}
