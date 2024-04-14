package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a user is denied access to a resource.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAccessDeniedException extends RuntimeException {
    /**
     * Constructs a new UserAccessDeniedException with the specified detail message.
     * @param message The detail message.
     */
    private UserAccessDeniedException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a FORBIDDEN status and the specified detail message.
     * @param text The message indicating the reason for access denial.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String text) {
        UserAccessDeniedException exception = new UserAccessDeniedException(text);
        return new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
    }
}
