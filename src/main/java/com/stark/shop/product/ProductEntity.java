package com.stark.shop.product;

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
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    private Integer price;
    private Integer inStock;
    private String imageURL;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
        if (name != null) {
            name = name.toLowerCase();
        }
    }

    public ProductEntity() {
    }

    public ProductEntity(String name, String description, String category, Integer price, Integer inStock,
            String imageURL) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.inStock = inStock;
        this.imageURL = imageURL;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getInStock() {
        return inStock;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setName(String name) {
        name = name.toLowerCase();
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    @Override
    public String toString() {
        return "ProductEntity [category=" + category + ", dateCreated=" + dateCreated + ", description=" + description
                + ", id=" + id + ", image=" + imageURL + ", name=" + name + ", price=" + price + ", inStock=" + inStock
                + "]";
    }
    
}
