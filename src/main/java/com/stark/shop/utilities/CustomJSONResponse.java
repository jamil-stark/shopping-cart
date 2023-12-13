package com.stark.shop.utilities;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomJSONResponse {
    public ResponseEntity<Map<String, Object>> returnStatusAndMessage(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<Map<String, Object>> returnStatusAndMessage(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }


    public ResponseEntity<Map<String, Object>> returnStatusAndMessage(HttpStatus status, String message, Object data, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        response.put("token", token);
        return new ResponseEntity<>(response, status);
    }
}
