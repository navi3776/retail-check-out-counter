package com.shopping_cart.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_category_xref")
public class ProductCategoryXref implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productCategoryId;
	private long productId;
	private long categoryId;

	/*@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name = "prodcutCategoryXrefs",insertable=false,updatable=false)
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductCategoryXref(long productId, long categoryId) {
		this.productId = productId;
		this.categoryId = categoryId;
	}*/

	public ProductCategoryXref() {
	}

	public long getProductTaxCategoryId() {
		return productCategoryId;
	}

	public void setProductTaxCategoryId(long productTaxCategoryId) {
		this.productCategoryId = productTaxCategoryId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getTaxCategoryId() {
		return categoryId;
	}

	public void setTaxCategoryId(long taxCategoryId) {
		this.categoryId = taxCategoryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (productId ^ (productId >>> 32));
		result = prime * result
				+ (int) (categoryId ^ (categoryId >>> 32));
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
		ProductCategoryXref other = (ProductCategoryXref) obj;
		if (productId != other.productId)
			return false;
		if (categoryId != other.categoryId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProdcutTaxCategoryXref [productTaxCategoryId="
				+ productCategoryId + ", productId=" + productId
				+ ", taxCategoryId=" + categoryId + "]";
	}
	
	

}
