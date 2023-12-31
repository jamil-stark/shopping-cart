package com.stark.shop.product;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products/")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductEntity product){
        return productService.createProduct(product);
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(){
        return productService.getAllProducts();
    }


    @GetMapping(path = "{productId}/")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable("productId") Long productId){
        return productService.getProduct(productId);
    }


    @DeleteMapping(path = "{productId}/")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable("productId") Long productId){
        return productService.deleteProduct(productId);
    }
}
