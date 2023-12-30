package com.stark.shop.orderItem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    Optional<OrderItemEntity> findByOrderId(Long orderId);
    Optional<OrderItemEntity> findByProductId(Long productId);
    Optional<OrderItemEntity> findByOrderIdAndProductId(Long orderId, Long productId);
    Optional<OrderItemEntity> deleteByOrderId(Long orderId);
}
