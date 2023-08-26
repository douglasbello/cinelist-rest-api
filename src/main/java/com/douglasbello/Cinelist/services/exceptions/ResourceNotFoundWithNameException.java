package com.douglasbello.Cinelist.services.exceptions;

import java.io.Serial;

public class ResourceNotFoundWithNameException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundWithNameException(String name) {
        super("Resource not found with name: " + name);
    }   
}
