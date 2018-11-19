package com.shopping_cart.domain;

import java.io.Serializable;
import java.util.Objects;

public class CheckOutProduct implements Serializable {

    private long productId;
    private long unitsOrdered;
    private double totalCost;
    private double applicableTaxes;

    CheckOutProduct(OrderedProductBuilder orderedProductBuilder) {
        this.productId = orderedProductBuilder.productId;
        this.unitsOrdered = orderedProductBuilder.unit;
    }

    CheckOutProduct() {
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getApplicableTaxes() {
        return applicableTaxes;
    }

    public void setApplicableTaxes(double applicableTaxes) {
        this.applicableTaxes = applicableTaxes;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUnitsOrdered() {
        return unitsOrdered;
    }

    public void setUnitsOrdered(long unitsOrdered) {
        this.unitsOrdered = unitsOrdered;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckOutProduct that = (CheckOutProduct) o;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "CheckOutProduct{" +
                "productId=" + productId +
                ", unitsOrdered=" + unitsOrdered +
                ", totalCost=" + totalCost +
                ", applicableTaxes=" + applicableTaxes +
                '}';
    }

    public static class OrderedProductBuilder {

        private long productId;
        private long unit;

        public OrderedProductBuilder(long productId) {
            this.productId = productId;
        }

        public OrderedProductBuilder withQuantity(long unit) {
            this.unit = unit;
            return this;
        }

        public CheckOutProduct build() {
            return new CheckOutProduct(this);
        }

    }

}
