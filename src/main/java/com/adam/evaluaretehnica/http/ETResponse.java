package com.adam.evaluaretehnica.http;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ETResponse(
        LocalDateTime time,
        HttpStatus httpStatus,
        int code,
        String path,
        ResponsePayload payload
) {
    public ETResponse(HttpStatus httpStatus, String path, ResponsePayload payload){
        this(
                LocalDateTime.now(),
                httpStatus,
                httpStatus.value(),
                path,
                payload
        );
    }
}
