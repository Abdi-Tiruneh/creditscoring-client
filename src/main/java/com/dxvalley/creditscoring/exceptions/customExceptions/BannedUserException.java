package com.dxvalley.creditscoring.exceptions.customExceptions;

import org.springframework.security.core.AuthenticationException;

public class BannedUserException extends AuthenticationException {

    public BannedUserException() {
        super("Access Denied: Your account has been banned. Please contact support for further assistance.");
    }
}

