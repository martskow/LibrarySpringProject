package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a user is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    private UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a NOT_FOUND status and the specified detail message.
     * @param id The ID of the user that was not found.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String id) {
        UserNotFoundException exception = new UserNotFoundException(String.format("User %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}