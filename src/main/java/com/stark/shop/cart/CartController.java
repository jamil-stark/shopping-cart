package com.stark.shop.cart;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stark.shop.dto.CartItemRequest;

@RestController
@RequestMapping("cart/")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createCart(@RequestBody List<CartItemRequest> cartItems, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return cartService.createCart(cartItems, token);
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return cartService.getCart(token);
    }
}