package com.dxvalley.creditscoring.exceptions.customExceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
        super("Service is temporarily unavailable");
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
