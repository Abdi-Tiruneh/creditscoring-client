package com.dxvalley.creditscoring.exceptions.customExceptions;

public class PaymentCannotProcessedException extends RuntimeException {
    public PaymentCannotProcessedException(String message) {
        super(message);
    }
}