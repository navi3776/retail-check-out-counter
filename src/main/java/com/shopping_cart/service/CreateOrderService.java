package com.shopping_cart.service;

import com.shopping_cart.domain.BillDetails;
import com.shopping_cart.domain.CheckOutOrderDetails;

public interface CreateOrderService {

    BillDetails createOrder(CheckOutOrderDetails orderDetails);

}
