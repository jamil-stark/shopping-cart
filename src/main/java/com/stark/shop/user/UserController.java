package com.stark.shop.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @DeleteMapping(path = "{userId}/")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping(path = "login/")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> loginRequest) {
        return userService.loginUser(loginRequest);
    }    

    @GetMapping(path = "get-user-by-token/")
    public ResponseEntity<Map<String, Object>> getUserByToken(@RequestBody Map<String, String> tokenRequest) {
        return userService.getUserByToken(tokenRequest);
    }

}
