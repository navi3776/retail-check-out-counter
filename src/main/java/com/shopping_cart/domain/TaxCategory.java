package com.shopping_cart.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tax_category")
public class TaxCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryId;
    private String name;
    private double value;

    public TaxCategory(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @OneToOne(mappedBy = "category")
    private Product product;

    public TaxCategory() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (categoryId ^ (categoryId >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        TaxCategory other = (TaxCategory) obj;
        if (categoryId != other.categoryId)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TaxCategory [categoryId=" + categoryId + ", name=" + name
                + ", value=" + value + "]";
    }

}
