package com.example.technologiesieciowe.service.error.QueueErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a place in the queue is not found.
 */
public class QueueNotFoundException extends RuntimeException {
    /**
     * Constructs a new QueueNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    private QueueNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResponseStatusException with a NOT_FOUND status and the specified detail message.
     * @param id The ID of the place in the queue.
     * @return The created ResponseStatusException.
     */
    public static ResponseStatusException create(String id) {
        QueueNotFoundException exception = new QueueNotFoundException(String.format("A place %s in the queue was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}