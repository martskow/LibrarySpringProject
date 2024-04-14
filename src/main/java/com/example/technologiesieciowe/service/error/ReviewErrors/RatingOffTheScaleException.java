package com.example.technologiesieciowe.service.error.ReviewErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a rating is not within the valid range.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class RatingOffTheScaleException extends RuntimeException {
    /**
     * Constructs a new RatingOffTheScaleException with the specified detail message.
     * @param message The detail message.
     */
    private RatingOffTheScaleException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a CONFLICT status and the specified detail message.
     * @param rating The rating value that is off the scale.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String rating) {
        RatingOffTheScaleException exception = new RatingOffTheScaleException(String.format("The %s rating is not between 1 and 10", rating));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}