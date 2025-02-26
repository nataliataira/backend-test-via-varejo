package com.via_varejo.backend_test.service;

import com.via_varejo.backend_test.domain.payment.Payment;
import com.via_varejo.backend_test.domain.product.Product;
import com.via_varejo.backend_test.domain.product.ProductRequestDTO;
import com.via_varejo.backend_test.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public Payment createPayment(ProductRequestDTO data, Product product) {
        Payment newPayment = new Payment();
        newPayment.setDownPayment(data.downPayment());
        newPayment.setInstallments(data.installments());
        newPayment.setProduct(product);
        product.setPayment(newPayment);

        return repository.save(newPayment);
    }
}
