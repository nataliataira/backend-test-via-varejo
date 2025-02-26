package com.via_varejo.backend_test.domain.product;

public record ProductRequestDTO(int code, String name, Double price, Double downPayment, int installments) {
}
