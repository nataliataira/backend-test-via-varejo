package com.via_varejo.backend_test.exception;

public class PaymentException extends RuntimeException {
    public PaymentException() {
        super("Erro no pagamento.");
    }

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
