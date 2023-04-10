package com.adam.evaluaretehnica.exception;

import com.adam.evaluaretehnica.http.ResponsePayload;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public record ErrorResponse(
        boolean isError,
        String errorMessage
) implements ResponsePayload {
    public ErrorResponse(
            String errorMessage) {
        this(true, errorMessage);
    }
}
