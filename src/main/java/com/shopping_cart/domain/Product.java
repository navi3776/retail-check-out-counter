package com.shopping_cart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    private String productName;
    private double price;
    private long unitsInInventory;
    private String description;
    private String manufacturer;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "product_category_xref", joinColumns = @JoinColumn(name = "productId"), inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "order_product_xref", joinColumns = @JoinColumn(name = "productId"), inverseJoinColumns = @JoinColumn(name = "orderId"))
    private Set<Orders> orders = new HashSet<>();

    public Product(ProductBuilder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.price = builder.price;
        this.unitsInInventory = builder.quanity;
        this.description = builder.description;
        this.manufacturer = builder.manufacturer;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Product() {
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getUnitsInInventory() {
        return unitsInInventory;
    }

    public void setUnitsInInventory(long unitsInInventory) {
        this.unitsInInventory = unitsInInventory;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (productId ^ (productId >>> 32));
        result = prime * result
                + ((productName == null) ? 0 : productName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (productId != other.productId)
            return false;
        if (productName == null) {
            if (other.productName != null)
                return false;
        } else if (!productName.equals(other.productName))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName="
                + productName + ", price=" + price + ", unitsInInventory=" + unitsInInventory
                + ", description=" + description + ", manufacturer="
                + manufacturer + ", categories=" + categories + "]";
    }


    public static class ProductBuilder {

        private long productId;
        private String productName;
        private double price;
        private long quanity;
        private String description;
        private String manufacturer;

        public ProductBuilder(String productName) {
            this.productName = productName;
        }

        public ProductBuilder withQuantity(long quantity) {
            this.quanity = quantity;
            return this;
        }

        public ProductBuilder withPrice(double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }

}
