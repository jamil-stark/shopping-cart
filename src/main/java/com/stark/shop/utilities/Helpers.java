package com.stark.shop.utilities;

import java.util.Optional;

import com.stark.shop.user.UserEntity;
import com.stark.shop.user.UserRepository;

public class Helpers {

    public static UserEntity validateTokenAndGetUser(String token, UserRepository userRepository) {
        try {
            if (token == null || token.isEmpty()) {
                throw new RuntimeException("Token is required");
            }

            String[] tokenParts = token.split(" ");
            if (tokenParts.length != 2) {
                throw new RuntimeException("Invalid token");
            }

            String tokenType = tokenParts[0];

            if (!tokenType.equals("Bearer")) {
                throw new RuntimeException("Invalid token type");
            }

            String tokenValue = tokenParts[1];
            Optional<UserEntity> userOptional = userRepository.findByToken(tokenValue);
            if (!userOptional.isPresent()) {
                throw new RuntimeException("Invalid token");
            }

            return userOptional.get();
        } catch (Exception e) {
            throw e;
        }
        
    }
}
