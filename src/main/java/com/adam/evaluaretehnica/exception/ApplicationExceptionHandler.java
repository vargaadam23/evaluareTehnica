package com.adam.evaluaretehnica.exception;

import com.adam.evaluaretehnica.http.ETResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private final ObjectMapper objectMapper;

    //Generic runtime exceptions
    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(
                new ETResponse(
                        HttpStatus.CONFLICT,
                        ((ServletWebRequest) request).getRequest().getRequestURI().toString(),
                        new ErrorResponse(ex.getMessage()
                        )
                ),
                HttpStatus.CONFLICT
        );
    }

    //Jwt token expired exception thrown in auth filter
    @ExceptionHandler(value
            = {ExpiredJwtException.class})
    public ResponseEntity<ETResponse> handleJwtExpired(ExpiredJwtException exception, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ETResponse(
                        HttpStatus.UNAUTHORIZED,
                        request.getRequestURI(),
                        new ErrorResponse(
                                "Your token has expired! Please authenticate to access the desired resource"
                        )
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

    //Other authentication related exceptions + Status change exceptions
    @ExceptionHandler(value
            = {AuthenticationException.class, UnauthorizedStatusChangeException.class, UsernameNotFoundException.class})
    public ResponseEntity<ETResponse> handleAuthenticationException(AuthenticationException exception, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ETResponse(
                        HttpStatus.UNAUTHORIZED,
                        request.getRequestURI(),
                        new ErrorResponse(
                                exception.getMessage()
                        )
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

    //Other generic exceptions
    @ExceptionHandler(value
            = {Exception.class, IOException.class, UserAlreadyExistsException.class, NotEnoughTokensException.class, QuestCreationException.class})
    public ResponseEntity<ETResponse> handleOtherExceptions(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ETResponse(
                        HttpStatus.BAD_REQUEST,
                        request.getRequestURI().toString(),
                        new ErrorResponse(
                                exception.getMessage()
                        )
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    //Validation exceptions
    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        try {
            return new ResponseEntity<>(
                    new ETResponse(
                            HttpStatus.BAD_REQUEST,
                            ((ServletWebRequest) request).getRequest().getRequestURI().toString(),
                            new ErrorResponse(
                                    serializeFieldErrors(ex.getFieldErrors())
                            )
                    ),
                    HttpStatus.BAD_REQUEST
            );
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(
                    new ETResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            ((ServletWebRequest) request).getRequest().getRequestURI().toString(),
                            new ErrorResponse(
                                    "Error serializing field validation errors"
                            )
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private String serializeFieldErrors(List<FieldError> fieldErrorList) throws JsonProcessingException {
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrorList) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return objectMapper.writeValueAsString(errors);
    }

}
