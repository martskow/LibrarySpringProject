package com.example.technologiesieciowe.service.error.LoanErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a loan is not found.
 */
public class LoanNotFoundException extends RuntimeException {
    /**
     * Constructs a new LoanNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    private LoanNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a NOT_FOUND status and the specified detail message.
     * @param id The ID of the loan.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String id) {
        LoanNotFoundException exception = new LoanNotFoundException(String.format("Loan %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
