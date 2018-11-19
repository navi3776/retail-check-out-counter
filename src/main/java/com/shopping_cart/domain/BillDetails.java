package com.shopping_cart.domain;

import java.io.Serializable;
import java.util.List;

public class BillDetails implements Serializable {

    private long customerId;
    private long orderId;
    private CustomerType customerType;
    private double totalCost;
    private double totalSalesTax;
    private String returnMessage;
    private List<CheckOutProduct> checkOutProducts;
    private CurrencyType currencyType;

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

    public List<CheckOutProduct> getCheckOutProducts() {
        return checkOutProducts;
    }

    public void setCheckOutProducts(List<CheckOutProduct> checkOutProducts) {
        this.checkOutProducts = checkOutProducts;
    }
}
