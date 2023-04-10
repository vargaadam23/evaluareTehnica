package com.adam.evaluaretehnica.exception;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizedStatusChangeException extends AuthenticationException {
    public UnauthorizedStatusChangeException(String msg) {
        super(msg);
    }
}
