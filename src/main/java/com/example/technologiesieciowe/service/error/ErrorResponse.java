package com.example.technologiesieciowe.service.error;

import java.time.LocalDateTime;

/**
 * Represents an error response returned by the API.
 */
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    /**
     * Constructs an ErrorResponse object with the given parameters.
     * @param timestamp The timestamp when the error occurred.
     * @param status The HTTP status code of the error.
     * @param error The error type.
     * @param message A description of the error.
     */
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    /**
     * Returns the timestamp of the error.
     * @return The timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the HTTP status code of the error.
     * @return The status code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns the type of the error.
     * @return The error type.
     */
    public String getError() {
        return error;
    }

    /**
     * Returns the description of the error.
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }
}