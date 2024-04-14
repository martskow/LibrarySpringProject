package com.example.technologiesieciowe.service.error.BookErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a book is not found.
 */
public class BookNotFoundException extends RuntimeException {
    /**
     * Constructs a new BookNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    private BookNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a NOT_FOUND status and the specified detail message.
     * @param id The ID of the book.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String id) {
        BookNotFoundException exception = new BookNotFoundException(String.format("Book %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
