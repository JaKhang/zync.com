package com.zync.network.core.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

import static com.zync.network.core.exceptions.Error.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {AuthenticationException.class, InsufficientAuthenticationException.class})
    public ResponseEntity<ErrorResponse> authenticationExceptionHandler(AuthenticationException ex) {
        Error error = Error.UNKNOWN;
        if (ex instanceof DisabledException){
            error = Error.ACCOUNT_DISABLED;
        } else if (ex instanceof BadCredentialsException){
            error = Error.BAD_CREDENTIALS;
        } else if (ex instanceof UsernameNotFoundException){
            error = Error.USER_NOT_FOUND;
        }
        return ResponseEntity.status(UNAUTHORIZED.getHttpCode()).body(new ErrorResponse(error.getErrorCode(),error.getHttpCode(), ex.getMessage()));
    }

    @ExceptionHandler(value = {AbstractSystemException.class})
    public ResponseEntity<ErrorResponse> handleSystemException(AbstractSystemException ex) {
        Error error = ex.getError();
        return ResponseEntity.status(error.getHttpCode()).body(new ErrorResponse(error.getErrorCode(),error.getHttpCode(), error.getMessage()));
    }


}
