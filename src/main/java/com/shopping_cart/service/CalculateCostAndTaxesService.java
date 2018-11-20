package com.shopping_cart.service;

import com.shopping_cart.domain.BillDetails;
import com.shopping_cart.domain.Category;
import com.shopping_cart.domain.CheckOutProduct;
import com.shopping_cart.domain.Product;

import java.util.List;
import java.util.Set;

public interface CalculateCostAndTaxesService {

    void calculateTotalCostAndTaxesForOrderedProduct(CheckOutProduct checkOutProduct, Product product);

    double calculateTaxesForOrderedProduct(double totalCost, List<Category> categoryList);

    void updateTotalCostAndTaxesForAllOrderedProducts(BillDetails billDetails, CheckOutProduct checkOutProduct);

}
