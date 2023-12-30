package com.stark.shop.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order/")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return orderService.createOrder(token);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return orderService.getOrdersAsAUser(token);
    }

    @GetMapping("all/")
    public ResponseEntity<Map<String, Object>> getAllOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return orderService.getAllOrders(token);
    }
}
