package com.douglasbello.Cinelist.services.exceptions;

import java.io.Serial;
import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(UUID id) {
        super("Resource not found with id: " + id);
    }
}
