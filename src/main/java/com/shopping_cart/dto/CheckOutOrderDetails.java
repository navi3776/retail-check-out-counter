package com.shopping_cart.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CheckOutOrderDetails implements Serializable {

    private long customerId;
    private List<OrderedProduct> orderedProducts;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckOutOrderDetails that = (CheckOutOrderDetails) o;
        return customerId == that.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public String toString() {
        return "CheckOutOrderDetails{" +
                "customerId=" + customerId +
                ", orderedProducts=" + orderedProducts +
                '}';
    }
}