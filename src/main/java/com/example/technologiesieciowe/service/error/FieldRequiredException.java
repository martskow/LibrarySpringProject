package com.example.technologiesieciowe.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Represents an exception indicating that a required field is missing.
 */
public class FieldRequiredException extends RuntimeException {
    /**
     * Constructs a FieldRequiredException with the specified detail message.
     * @param message The detail message.
     */
    private FieldRequiredException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with the appropriate HTTP status code and message.
     * @param fieldName The name of the field that is required.
     * @return The ResponseStatusException.
     */
    public static ResponseStatusException create(String fieldName) {
        FieldRequiredException exception = new FieldRequiredException(
                String.format("%s is required and cannot be null.", fieldName));
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
    }
}