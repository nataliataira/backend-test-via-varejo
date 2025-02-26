package com.via_varejo.backend_test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.via_varejo.backend_test.domain.product.Product;
import com.via_varejo.backend_test.domain.product.ProductRequestDTO;
import com.via_varejo.backend_test.domain.product.ProductResponseDTO;
import com.via_varejo.backend_test.exception.PaymentException;
import com.via_varejo.backend_test.exception.ProductPriceException;
import com.via_varejo.backend_test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<List<ProductResponseDTO>> create(@RequestBody ProductRequestDTO body) throws JsonProcessingException, ProductPriceException, PaymentException {
        Product newProduct = this.productService.createProduct(body);
        List<ProductResponseDTO> installmentsInfo = productService.calculateInstallmentValue(newProduct);
        return ResponseEntity.ok(installmentsInfo);
    }
}
