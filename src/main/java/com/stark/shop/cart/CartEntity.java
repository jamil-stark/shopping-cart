package com.stark.shop.cart;

import java.util.Date;

import com.stark.shop.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated")
    private Date dateUpdated;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }


    public CartEntity() {
    }


    public CartEntity(UserEntity user) {
        this.user = user;
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


    public void setUser(UserEntity user) {
        this.user = user;
    }


    public UserEntity getUser() {
        return this.user;
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