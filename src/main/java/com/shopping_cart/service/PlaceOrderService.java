package com.shopping_cart.service;

import com.shopping_cart.dto.BillDetails;
import com.shopping_cart.dto.CheckOutOrderDetails;

public interface PlaceOrderService {

    BillDetails placeOrder(CheckOutOrderDetails checkOutOrderDetails);

}
