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

    private double downPayment;
    private Integer installments;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
