package com.douglasbello.Cinelist.model.controllers;

import com.douglasbello.Cinelist.model.entities.Admin;
import com.douglasbello.Cinelist.model.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/admins")
public class AdminController {
    @Autowired
    private AdminService service;

    @GetMapping
    public ResponseEntity<List<Admin>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Admin> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Admin> insert(@RequestBody Admin obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}