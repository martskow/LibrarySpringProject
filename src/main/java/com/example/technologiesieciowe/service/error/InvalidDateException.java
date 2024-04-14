package com.example.technologiesieciowe.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when an invalid date format or value is encountered.
 */
public class InvalidDateException extends RuntimeException {

    /**
     * Constructs a new InvalidDateException with the specified detail message.
     *
     * @param message the detail message
     */
    private InvalidDateException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with the specified text.
     *
     * @param text the text describing the exception
     * @return the ResponseStatusException created
     */
    public static ResponseStatusException create(String text) {
        InvalidDateException exception = new InvalidDateException(String.format("%s", text));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
