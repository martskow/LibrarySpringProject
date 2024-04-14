package com.example.technologiesieciowe.service.error.BookErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when there are no available copies of a book.
 */
public class NotAvailableCopiesException extends RuntimeException {
    /**
     * Constructs a new NotAvailableCopiesException with the specified detail message.
     * @param message The detail message.
     */
    private NotAvailableCopiesException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a CONFLICT status and the specified detail message.
     * @param bookName The name of the book.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String bookName) {
        NotAvailableCopiesException exception = new NotAvailableCopiesException(String.format("There are currently no copies of the book %s available", bookName));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
