package com.adam.evaluaretehnica.security.authentication;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
