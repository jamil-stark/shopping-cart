package com.stark.shop.cart;

import com.stark.shop.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public CartEntity() {
    }

    public CartEntity(Long cartId, UserEntity user) {
        this.cartId = cartId;
        this.user = user;
    }

    public CartEntity(UserEntity user) {
        this.user = user;
    }

    public Long getCartId() {
        return this.cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


}
