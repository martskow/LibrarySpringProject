package com.example.technologiesieciowe.service.error.BookErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a book with the same ISBN already exists.
 */
public class IsbnAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new IsbnAlreadyExistsException with the specified detail message.
     * @param message The detail message.
     */
    private IsbnAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a CONFLICT status and the specified detail message.
     * @param isbn The ISBN number.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String isbn) {
        IsbnAlreadyExistsException exception = new IsbnAlreadyExistsException(String.format("Book with ISBN number: %s already exists.", isbn));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
