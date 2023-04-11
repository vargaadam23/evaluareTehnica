package com.adam.evaluaretehnica.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

//Response template for the application
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

        Logger logger = LoggerFactory.getLogger(ETResponse.class);
        logger.info("Sending response "+httpStatus+" on path "+path+" at"+ LocalDateTime.now().toString());

    }
}
