package com.via_varejo.backend_test.repositories;

import com.via_varejo.backend_test.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
