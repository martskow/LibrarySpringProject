package com.example.technologiesieciowe.service.error.BookDetailsErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
/**
 * Exception thrown when book details are not found.
 */
public class BookDetailsNotFoundException extends RuntimeException {
    /**
     * Constructs a new BookDetailsNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    private BookDetailsNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a NOT_FOUND status and the specified detail message.
     * @param detail The detail identifying the book details.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String detail) {
        BookDetailsNotFoundException exception = new BookDetailsNotFoundException(String.format("Book details by %s was not found", detail));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}