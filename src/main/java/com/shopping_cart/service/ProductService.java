package com.shopping_cart.service;

import com.shopping_cart.domain.BillDetails;
import com.shopping_cart.domain.CheckOutOrderDetails;
import com.shopping_cart.domain.CheckOutProduct;
import com.shopping_cart.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    void updateProductsInventory(List<Product> productList);

    void setProductsLeft(CheckOutProduct checkOutProduct, Product product);

    boolean isProductOrderedWithValidQuantity(CheckOutProduct checkOutProduct, Product product, BillDetails billDetails);

    Map<Long, Product> getProductDetailsForProductIds(CheckOutOrderDetails checkOutOrderDetails);

    Iterable<Long> getProductIds(List<CheckOutProduct> checkOutProducts);

}
