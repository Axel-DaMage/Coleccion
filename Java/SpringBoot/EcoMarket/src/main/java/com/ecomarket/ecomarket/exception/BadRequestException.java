package com.ecomarket.ecomarket.exception;

/**
 * Excepción lanzada cuando los datos de entrada no son válidos.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
