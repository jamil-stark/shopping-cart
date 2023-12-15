package com.stark.shop.cart;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.stark.shop.cartItem.CartItemEntity;
import com.stark.shop.cartItem.CartItemRepository;
import com.stark.shop.dto.CartItemRequest;
import com.stark.shop.product.ProductEntity;
import com.stark.shop.product.ProductRepository;
import com.stark.shop.user.UserEntity;
import com.stark.shop.user.UserRepository;
import com.stark.shop.utilities.CustomJSONResponse;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CustomJSONResponse customJSONResponse = new CustomJSONResponse();

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
            UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<Map<String, Object>> createCart(@RequestBody List<CartItemRequest> cartItems, String token) {
        try {
            token = token.substring(7);
            Optional<UserEntity> userOptional = userRepository.findByToken(token);
            if (!userOptional.isPresent()) {
                return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, token);
            }

            UserEntity user = userOptional.get();
            Optional<CartEntity> cartOptional = cartRepository.findByUserId(user.getId());
            if (cartOptional.isPresent()) {
                for (CartItemRequest cartItemRequest : cartItems) {
                    Optional<ProductEntity> productOptional = productRepository
                            .findById(cartItemRequest.getProductId());
                    if (!productOptional.isPresent()) {
                        return customJSONResponse.returnStatusAndMessage(
                                HttpStatus.NOT_FOUND,
                                "Product with id " + cartItemRequest.getProductId() + " not found");
                    } else if (cartItemRepository
                            .findByCartIdAndProductId(cartOptional.get().getId(), cartItemRequest.getProductId())
                            .isPresent()) {
                        CartItemEntity cartItem = cartItemRepository
                                .findByCartIdAndProductId(cartOptional.get().getId(), cartItemRequest.getProductId())
                                .get();
                        cartItem.setQuantity(cartItemRequest.getQuantity());
                        cartItemRepository.save(cartItem);
                        continue;
                    }

                    CartEntity cart = cartOptional.get();
                    CartItemEntity cartItem = new CartItemEntity();
                    cartItem.setCart(cart);
                    ProductEntity product = productOptional.get();
                    cartItem.setProduct(product);
                    cartItem.setQuantity(cartItemRequest.getQuantity());
                    cartItemRepository.save(cartItem);
                }

                List<CartItemEntity> cartItemsList = cartItemRepository.findAllByCartId(cartOptional.get().getId());
                return customJSONResponse.returnStatusAndMessage(
                        HttpStatus.CREATED,
                        "Cart created successfully",
                        cartItemsList,
                        cartItemsList.size());
            }

            CartEntity cart = new CartEntity();
            cart.setUser(user);
            cartRepository.save(cart);
            for (CartItemRequest cartItemRequest : cartItems) {
                Optional<ProductEntity> productOptional = productRepository.findById(cartItemRequest.getProductId());
                if (!productOptional.isPresent()) {
                    return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, "Product not found");
                }

                CartItemEntity cartItem = new CartItemEntity();
                cartItem.setCart(cart);
                ProductEntity product = productOptional.get();
                cartItem.setProduct(product);
                cartItem.setQuantity(cartItemRequest.getQuantity());
                cartItemRepository.save(cartItem);
            }
            List<CartItemEntity> cartItemsList = cartItemRepository.findAllByCartId(cart.getId());
            return customJSONResponse.returnStatusAndMessage(
                    HttpStatus.CREATED,
                    "Cart created successfully",
                    cartItemsList,
                    cartItemsList.size());
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<Map<String, Object>> getCart(String token) {
        try {
            token = token.substring(7);
            Optional<UserEntity> userOptional = userRepository.findByToken(token);
            if (!userOptional.isPresent()) {
                return customJSONResponse.returnStatusAndMessage(
                        HttpStatus.NOT_FOUND,
                        "User not found");
            }

            UserEntity user = userOptional.get();
            Optional<CartEntity> cartOptional = cartRepository.findByUserId(user.getId());
            if (!cartOptional.isPresent()) {
                return customJSONResponse.returnStatusAndMessage(
                        HttpStatus.BAD_REQUEST,
                        "Cart not found");
            }

            CartEntity cart = cartOptional.get();
            List<CartItemEntity> cartItems = cartItemRepository.findAllByCartId(cart.getId());
            return customJSONResponse.returnStatusAndMessage(
                    HttpStatus.OK,
                    "Cart retrieved successfully",
                    cartItems,
                    cartItems.size());

        } catch (Exception e) {
            throw e;
        }
    }

}