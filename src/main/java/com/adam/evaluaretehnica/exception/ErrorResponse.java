package com.adam.evaluaretehnica.exception;

import com.adam.evaluaretehnica.http.ResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

//Record used for all error responses
public record ErrorResponse(
        boolean isError,
        String errorMessage
) implements ResponsePayload {
    public ErrorResponse(
            String errorMessage) {
        this(true, errorMessage);

        Logger logger = LoggerFactory.getLogger(ErrorResponse.class);
        logger.error("Exception on path at timestamp" + LocalDateTime.now() + " with message:" + errorMessage);
    }
}
