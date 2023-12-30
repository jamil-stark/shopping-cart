package com.stark.shop.order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stark.shop.cart.CartEntity;
import com.stark.shop.cart.CartRepository;
import com.stark.shop.cartItem.CartItemEntity;
import com.stark.shop.cartItem.CartItemRepository;
import com.stark.shop.orderItem.OrderItemEntity;
import com.stark.shop.orderItem.OrderItemRepository;
import com.stark.shop.product.ProductRepository;
import com.stark.shop.user.UserEntity;
import com.stark.shop.user.UserRepository;
import com.stark.shop.utilities.CustomJSONResponse;
import com.stark.shop.utilities.Helpers;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomJSONResponse customJSONResponse = new CustomJSONResponse();

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository,
            CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public ResponseEntity<Map<String, Object>> createOrder(String token) {
        try{
            UserEntity user = Helpers.validateTokenAndGetUser(token, userRepository);
            Optional<CartEntity> cartOptional = cartRepository.findByUserId(user.getId());
            if(!cartOptional.isPresent()){
                return customJSONResponse.returnStatusAndMessage(HttpStatus.NOT_FOUND, "Cart not found");
            }
            List<CartItemEntity> cartItemEntities = cartItemRepository.findAllByCartId(cartOptional.get().getId());
            if(cartItemEntities.isEmpty()){
                return customJSONResponse.returnStatusAndMessage(HttpStatus.NOT_FOUND, "Cart is empty");
            }

            // reduce product quantity
            for(CartItemEntity cartItemEntity : cartItemEntities){
                if(cartItemEntity.getQuantity() > cartItemEntity.getProduct().getInStock()){
                    return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, "Product " + cartItemEntity.getProduct().getName() + " is out of stock");
                }
                cartItemEntity.getProduct().setInStock(cartItemEntity.getProduct().getInStock() - cartItemEntity.getQuantity());
                productRepository.save(cartItemEntity.getProduct());
            }

            OrderEntity order = new OrderEntity(user, "pending");
            orderRepository.save(order);

            for(CartItemEntity cartItemEntity : cartItemEntities){
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setOrder(order);
                orderItem.setProduct(cartItemEntity.getProduct());
                orderItem.setQuantity(cartItemEntity.getQuantity());
                orderItemRepository.save(orderItem);
                cartItemRepository.delete(cartItemEntity);
            }
            cartRepository.delete(cartOptional.get());
            return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "Order created", order);
        }
         catch (Exception e) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public ResponseEntity<Map<String, Object>> getOrders(String token) {
        try{
            UserEntity user = Helpers.validateTokenAndGetUser(token, userRepository);
            List<OrderEntity> orders = orderRepository.findAllByUserId(user.getId());
            return customJSONResponse.returnStatusAndMessage(HttpStatus.OK, "Orders retrieved", orders);
        }
        catch (Exception e) {
            return customJSONResponse.returnStatusAndMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
    