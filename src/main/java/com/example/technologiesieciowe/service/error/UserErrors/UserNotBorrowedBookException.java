package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when attempting to add a review for a book that the user has not borrowed.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserNotBorrowedBookException extends RuntimeException {
    /**
     * Constructs a new UserNotBorrowedBookException with the specified detail message.
     * @param message The detail message.
     */
    private UserNotBorrowedBookException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a CONFLICT status and the specified detail message.
     * @param bookId The ID of the book that the user has not borrowed.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String bookId) {
        UserNotBorrowedBookException exception = new UserNotBorrowedBookException(String.format("User hasn't borrowed book %s", bookId));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
