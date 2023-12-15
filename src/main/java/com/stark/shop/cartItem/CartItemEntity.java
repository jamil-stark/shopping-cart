package com.stark.shop.cartItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stark.shop.cart.CartEntity;
import com.stark.shop.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private int quantity;


    public CartItemEntity() {
    }

    public CartItemEntity(CartEntity cart, ProductEntity product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItemEntity(Long cartItemId, CartEntity cart, ProductEntity product, int quantity) {
        this.cartItemId = cartItemId;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getCartItemId() {
        return this.cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }


    public CartEntity getCart() {
        return this.cart;
    }


    public void setCart(CartEntity cart) {
        this.cart = cart;
    }


    public ProductEntity getProduct() {
        return this.product;
    }


    public void setProduct(ProductEntity product) {
        this.product = product;
    }


    public int getQuantity() {
        return this.quantity;
    }

    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    @Override
    public String toString() {
        return "{" +
            " cartItemId='" + getCartItemId() + "'" +
            ", cart='" + getCart() + "'" +
            ", product='" + getProduct() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }


}
