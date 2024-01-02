package com.stark.shop.order;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stark.shop.orderItem.OrderItemEntity;
import com.stark.shop.user.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    @Column(name = "date_updated")
    private Date dateUpdated;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
        status = "pending";
    }

    public OrderEntity() {
    }

    public OrderEntity(UserEntity user) {
        this.user = user;
    }

    public OrderEntity(UserEntity user, String status) {
        this.user = user;
        this.status = status;
    }

    public OrderEntity(Long id, UserEntity user, String status, Date dateCreated, Date dateUpdated) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public OrderEntity(Long id, UserEntity user, String status, Date dateCreated, Date dateUpdated,
            List<OrderItemEntity> orderItems) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return this.id;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public List<OrderItemEntity> getOrderItems(){
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems){
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", user='" + getUser() + "'" +
                ", dateCreated='" + getDateCreated() + "'" +
                ", dateUpdated='" + getDateUpdated() + "'" +
                "}";
    }
}
