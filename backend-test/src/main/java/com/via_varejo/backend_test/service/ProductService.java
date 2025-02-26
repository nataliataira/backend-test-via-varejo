package com.via_varejo.backend_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.via_varejo.backend_test.domain.product.Product;
import com.via_varejo.backend_test.domain.product.ProductRequestDTO;
import com.via_varejo.backend_test.domain.product.ProductResponseDTO;
import com.via_varejo.backend_test.exception.PaymentException;
import com.via_varejo.backend_test.exception.ProductNullException;
import com.via_varejo.backend_test.exception.ProductPriceException;
import com.via_varejo.backend_test.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InterestRateApiService interestRateApiService;

    public Product createProduct(ProductRequestDTO data) throws ProductPriceException, PaymentException {
        validateInputs(data);
        Product newProduct = new Product();
        newProduct.setName(data.name());
        newProduct.setPrice(data.price());
        newProduct.setCode(data.code());
        repository.save(newProduct);

        this.paymentService.createPayment(data, newProduct);

        return newProduct;
    }
    
    public void validateInputs(ProductRequestDTO data) throws PaymentException, ProductPriceException {
        if (isInvalidProductData(data)) {
            throw new ProductNullException();
        }
        if (isInvalidPriceOrInstallments(data)) {
            throw new ProductPriceException();
        }
        if (isDownPaymentGreaterThanPrice(data)) {
            throw new PaymentException();
        }
        if (isFullPaymentWithInstallments(data)) {
            throw new PaymentException();
        }
    }

    private boolean isInvalidProductData(ProductRequestDTO data) {
        return data.code() <= 0 || data.name() == null || data.downPayment() == null;
    }

    private boolean isInvalidPriceOrInstallments(ProductRequestDTO data) {
        return data.installments() == 0 || data.price() <= 0;
    }

    private boolean isDownPaymentGreaterThanPrice(ProductRequestDTO data) {
        return data.downPayment() > data.price();
    }

    private boolean isFullPaymentWithInstallments(ProductRequestDTO data) {
        return data.price().equals(data.downPayment()) && data.installments() > 1;
    }

    public List<ProductResponseDTO> calculateInstallmentValue(Product product) throws JsonProcessingException {
        double price = product.getPrice();
        double downPayment = product.getPayment().getDownPayment();
        int installmentsNumber = product.getPayment().getInstallments();

        double installmentValue = (price - downPayment) / installmentsNumber;

        List<ProductResponseDTO> installmentsArray = new ArrayList<ProductResponseDTO>();
        for (int counter = 0; counter < installmentsNumber; counter++) {
            if (installmentsNumber == 1) {
                installmentsArray.add(new ProductResponseDTO(counter + 1, price, "0"));
            }
            else if(installmentsNumber <= 6) {
                installmentsArray.add(new ProductResponseDTO(counter + 1, installmentValue, "0"));
            } else if (installmentsNumber > 6) {
                installmentsArray.add(new ProductResponseDTO(counter + 1, installmentValue, interestRateApiService.getAccumulatedSelic().toString()));
            }
        }
        return installmentsArray;
    }
}
