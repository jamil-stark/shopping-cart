package com.stark.shop.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.stark.shop.utilities.CustomJSONResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomJSONResponse customJSONResponse = new CustomJSONResponse();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "Users retrieved successfully", users);
    }

    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(user.getUsername().toLowerCase());
        if (userOptional.isPresent()) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, "Username already taken");
        }
        userOptional = userRepository.findByEmail(user.getEmail().toLowerCase());
        if (userOptional.isPresent()) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, "Email already taken");
        }
        UserEntity newUser = userRepository.save(user);
        return customJSONResponse.returnStatusAndMessage(HttpStatus.CREATED, "User Created Successfully!", newUser, newUser.getToken());
    }

    public ResponseEntity<Map<String, Object>> deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(userId);
        return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "User deleted successfully");
    }

    public ResponseEntity<Map<String, Object>> loginUser(Map<String, String> loginRequest) {
        String username = (String) loginRequest.get("username");
        String password = (String) loginRequest.get("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, "Invalid username or password");
        }

        username = username.toLowerCase();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, "Invalid username or password");
        }

        UserEntity user = userOptional.get();
        if (!user.checkPassword(password)) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, "Invalid username or password");
        }

        return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "User logged in successfully", user, user.getToken());
    }
}