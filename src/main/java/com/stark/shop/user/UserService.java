package com.stark.shop.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Username already taken");
        }
        userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        UserEntity newUser = userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.value());
        response.put("message", "User created successfully");
        response.put("data", newUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Map<String, Object>> deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "User deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> loginUser(Map<String, String> loginRequest) {
        String username = (String) loginRequest.get("username");
        String password = (String) loginRequest.get("password");
        Map<String, Object> response = new HashMap<>();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Invalid username or password");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userOptional.get();
        if (!user.checkPassword(password)) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Invalid username or password");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Login successful");
        response.put("data", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}