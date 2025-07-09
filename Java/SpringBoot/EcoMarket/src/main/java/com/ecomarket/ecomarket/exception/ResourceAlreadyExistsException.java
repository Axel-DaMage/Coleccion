package com.ecomarket.ecomarket.exception;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe.
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String resource, String field, Object value) {
        super(String.format("%s ya existe con %s: %s", resource, field, value));
    }
}
