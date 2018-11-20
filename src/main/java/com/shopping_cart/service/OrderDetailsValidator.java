package com.shopping_cart.service;

import com.shopping_cart.domain.Product;
import com.shopping_cart.dto.BillDetails;
import com.shopping_cart.dto.CheckOutOrderDetails;
import com.shopping_cart.dto.OrderedProduct;

public interface OrderDetailsValidator {

    boolean checkIfOrderDetailsAreNullOrEmpty(CheckOutOrderDetails checkOutOrderDetails, BillDetails billDetails);

    boolean isProductOrderedWithValidQuantity(OrderedProduct orderedProduct, Product product, BillDetails billDetails);

}
