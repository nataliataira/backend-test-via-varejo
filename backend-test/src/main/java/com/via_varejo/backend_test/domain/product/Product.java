package com.via_varejo.backend_test.domain.product;

import com.via_varejo.backend_test.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private int code;
    private String name;
    private double price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Payment payment;
}
