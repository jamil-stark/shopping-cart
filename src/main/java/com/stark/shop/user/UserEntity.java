package com.stark.shop.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.RandomStringUtils;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;


@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true) 
    private String username;
    private String password;
    private String fullname;
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @JsonIgnore
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
        if (username != null) {
            username = username.toLowerCase();
        }
        if (email != null) {
            email = email.toLowerCase();
        }
        this.token = generateRandomToken();
    }

    public UserEntity() {
    }

    public UserEntity(String username, String password, String fullname, String email) {
        this.username = username;
        this.password = encodePassword(password);
        this.fullname = fullname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = encodePassword(password);
    }

    public boolean checkPassword(String plainPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword, this.password);
    }

    private String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private String generateRandomToken() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserEntity [dateCreated=" + dateCreated + ", email=" + email + ", fullname=" + fullname + ", id=" + id
                + ", password=" + password + ", username=" + username + "]";
    }

}