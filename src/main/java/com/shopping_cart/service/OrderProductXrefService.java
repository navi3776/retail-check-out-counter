package com.shopping_cart.service;

import com.shopping_cart.domain.Orders;
import com.shopping_cart.domain.Product;

import java.util.List;

public interface OrderProductXrefService {

    void saveOrderProductXref(Orders orders, List<Product> productList);

}
