package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when attempting to create a user that already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     * @param message The detail message.
     */
    private UserAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a CONFLICT status and the specified detail message.
     * @param userName The user name for which the duplication occurred.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String userName) {
        UserAlreadyExistsException exception = new UserAlreadyExistsException(String.format("User with user name: %s already exists.", userName));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
