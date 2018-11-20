package com.shopping_cart.dto;

import com.shopping_cart.domain.CurrencyType;
import com.shopping_cart.domain.CustomerType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BillDetails implements Serializable {

    private long customerId;
    private long orderId;
    private CustomerType customerType;
    private double totalCost;
    private double totalSalesTax;
    private String returnMessage;
    private CurrencyType currencyType;
    private Boolean isOrderSuccessful;
    private List<OrderedProduct> successfulOrderedProducts;
    private List<OrderedProduct> failedOrderedProducts;
    private Date orderDate;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderedProduct> getSuccessfulOrderedProducts() {
        return successfulOrderedProducts;
    }

    public void setSuccessfulOrderedProducts(List<OrderedProduct> successfulOrderedProducts) {
        this.successfulOrderedProducts = successfulOrderedProducts;
    }

    public List<OrderedProduct> getFailedOrderedProducts() {
        return failedOrderedProducts;
    }

    public void setFailedOrderedProducts(List<OrderedProduct> failedOrderedProducts) {
        this.failedOrderedProducts = failedOrderedProducts;
    }

    public Boolean getOrderSuccessful() {
        return isOrderSuccessful;
    }

    public void setOrderSuccessful(Boolean orderSuccessful) {
        isOrderSuccessful = orderSuccessful;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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
        BillDetails that = (BillDetails) o;
        return customerId == that.customerId &&
                orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderId);
    }

    @Override
    public String toString() {
        return "BillDetails{" +
                "customerId=" + customerId +
                ", orderId=" + orderId +
                ", customerType=" + customerType +
                ", totalCost=" + totalCost +
                ", totalSalesTax=" + totalSalesTax +
                ", returnMessage='" + returnMessage + '\'' +
                ", currencyType=" + currencyType +
                ", isOrderSuccessful=" + isOrderSuccessful +
                ", successfulOrderedProducts=" + successfulOrderedProducts +
                ", failedOrderedProducts=" + failedOrderedProducts +
                ", orderDate=" + orderDate +
                '}';
    }
}
