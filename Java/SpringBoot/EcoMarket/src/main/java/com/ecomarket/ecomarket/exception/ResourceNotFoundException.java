package com.ecomarket.ecomarket.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no se encuentra.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s no encontrado con %s: %s", resource, field, value));
    }
}
