package com.example.technologiesieciowe.service.error.LoanArchiveErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a loan archive is not found.
 */
public class LoanArchiveNotFoundException extends RuntimeException {
    /**
     * Constructs a new LoanArchiveNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    private LoanArchiveNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a NOT_FOUND status and the specified detail message.
     * @param id The ID of the loan archive.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String id) {
        LoanArchiveNotFoundException exception = new LoanArchiveNotFoundException(String.format("Archival loan %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}