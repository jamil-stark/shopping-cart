package com.stark.shop.cartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stark.shop.cart.CartEntity;
import com.stark.shop.product.ProductEntity;
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> deleteAllByCart(CartEntity cartId);
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);
    Optional<CartItemEntity> findByCartId(Long cartId);
    List<CartItemEntity> findAllByCartId(Long cartId);
    Optional<CartItemEntity> findByProductAndCartId(ProductEntity product, Long cartId);
}
