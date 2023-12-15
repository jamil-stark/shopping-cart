package com.stark.shop.dto;

public class CartItemRequest {
    private Long productId;
    private int quantity;


    public CartItemRequest() {
    }

    public CartItemRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
            " productId='" + getProductId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
