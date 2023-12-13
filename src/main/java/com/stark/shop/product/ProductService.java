package com.stark.shop.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.stark.shop.utilities.CustomJSONResponse;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomJSONResponse customJSONResponse = new CustomJSONResponse();

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductEntity product) {
        ProductEntity newProduct = productRepository.save(product);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.value());
        response.put("message", "Product created successfully");
        response.put("data", newProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Map<String, Object>> deleteProduct(Long productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(productId);
        return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "Product deleted successfully");
    }


    public ResponseEntity<Map<String, Object>> getProduct(Long productId) {
        Optional<ProductEntity> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.NOT_FOUND, "Product not found");
        }

        return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "Product retrieved successfully", productOptional.get());
    }

    public ResponseEntity<Map<String, Object>> getAllProducts() {
        return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "Products retrieved successfully", productRepository.findAll());
    }

}
