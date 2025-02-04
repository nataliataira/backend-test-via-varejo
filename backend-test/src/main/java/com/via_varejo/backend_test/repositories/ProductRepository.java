package com.via_varejo.backend_test.repositories;

import com.via_varejo.backend_test.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
