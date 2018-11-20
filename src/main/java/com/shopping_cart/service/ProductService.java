package com.shopping_cart.service;

import com.shopping_cart.domain.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void updateProductsInventory(List<Product> productList);

    Map<Long, Product> getProductDetailsForOrderedProductIds(Iterable<Long> productIds);

}
