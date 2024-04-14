package com.example.technologiesieciowe.service.error.ReviewErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a review is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {
    /**
     * Constructs a new ReviewNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    private ReviewNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a NOT_FOUND status and the specified detail message.
     * @param id The ID of the review that was not found.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String id) {
        ReviewNotFoundException exception = new ReviewNotFoundException(String.format("Review %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
