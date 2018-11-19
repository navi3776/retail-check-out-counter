package com.shopping_cart.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CheckOutOrderDetails implements Serializable {

    private long customerId;
    private long orderId;
    private CustomerType customerType;
    private double totalCost;
    private double totalSalesTax;
    private String returnMessage;
    private List<CheckOutProduct> checkOutProducts;

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckOutOrderDetails that = (CheckOutOrderDetails) o;
        return customerId == that.customerId &&
                orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderId);
    }

    public List<CheckOutProduct> getCheckOutProducts() {
        return checkOutProducts;
    }

    public void setCheckOutProducts(List<CheckOutProduct> checkOutProducts) {
        this.checkOutProducts = checkOutProducts;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalSalesTax() {
        return totalSalesTax;
    }

    public void setTotalSalesTax(double totalSalesTax) {
        this.totalSalesTax = totalSalesTax;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CheckOutOrderDetails{" +
                "customerId=" + customerId +
                ", orderId=" + orderId +
                ", customerType=" + customerType +
                ", totalCost=" + totalCost +
                ", totalSalesTax=" + totalSalesTax +
                ", returnMessage='" + returnMessage + '\'' +
                ", checkOutProducts=" + checkOutProducts +
                '}';
    }
}