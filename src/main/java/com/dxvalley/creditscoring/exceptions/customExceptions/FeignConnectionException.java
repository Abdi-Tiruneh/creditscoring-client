package com.dxvalley.creditscoring.exceptions.customExceptions;

import org.springframework.security.core.AuthenticationException;

public class FeignConnectionException extends AuthenticationException {

    public FeignConnectionException(String message) {
        super(message);
    }
}
