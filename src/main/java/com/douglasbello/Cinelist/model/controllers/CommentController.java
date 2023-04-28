package com.douglasbello.Cinelist.model.controllers;


import com.douglasbello.Cinelist.model.entities.Comment;
import com.douglasbello.Cinelist.model.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = service.findAll();
        return ResponseEntity.ok().body(comments);
    }
}