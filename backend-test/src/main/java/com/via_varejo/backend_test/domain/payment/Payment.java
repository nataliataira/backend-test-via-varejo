package com.via_varejo.backend_test.domain.payment;

import com.via_varejo.backend_test.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "payment")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    private double down_payment;
    private Integer installment;

    @OneToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
