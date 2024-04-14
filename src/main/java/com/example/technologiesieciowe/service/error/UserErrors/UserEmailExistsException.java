package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when attempting to create a user with an email address that already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserEmailExistsException extends RuntimeException {
    /**
     * Constructs a new UserEmailExistsException with the specified detail message.
     * @param message The detail message.
     */
    private UserEmailExistsException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a CONFLICT status and the specified detail message.
     * @param email The email address for which the duplication occurred.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String email) {
        UserEmailExistsException exception = new UserEmailExistsException(String.format("A user with the specified email address: %s already exists.", email));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
