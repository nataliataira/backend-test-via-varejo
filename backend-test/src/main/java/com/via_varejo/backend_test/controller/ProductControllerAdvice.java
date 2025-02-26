package com.via_varejo.backend_test.controller;

import com.via_varejo.backend_test.exception.PaymentException;
import com.via_varejo.backend_test.exception.ProductNullException;
import com.via_varejo.backend_test.exception.ProductPriceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProductNullException.class, ProductPriceException.class, PaymentException.class})
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message;
        if (ex instanceof ProductNullException) {
            message = "Verifique os campos obrigatórios do produto.";
        } else if (ex instanceof ProductPriceException) {
            message = "O preço do produto deve ser maior que zero.";
        } else if (ex instanceof PaymentException) {
            message = "O valor da entrada não pode ser maior que o preço total.";
        } else {
            message = "Erro desconhecido.";
        }

        return buildResponse(status, message);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("mensagem", message);
        return ResponseEntity.status(status).body(body);
    }
}
