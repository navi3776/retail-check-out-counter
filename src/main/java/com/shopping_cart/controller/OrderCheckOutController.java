package com.shopping_cart.controller;

import com.shopping_cart.dto.BillDetails;
import com.shopping_cart.dto.CheckOutOrderDetails;
import com.shopping_cart.service.PlaceOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderCheckOutController {

    Logger logger = LogManager.getLogger(OrderCheckOutController.class);

    @Autowired
    private PlaceOrderService placeOrderService;

    @RequestMapping(value = "/checkOutOrder/", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public BillDetails checkOutOrder(@RequestBody CheckOutOrderDetails checkOutOrderDetails) {
        logger.info(String.format(" Entering OrderCheckOutController for the current order with order details [%s]",checkOutOrderDetails));

        BillDetails billDetails = placeOrderService.placeOrder(checkOutOrderDetails);
        logger.info(String.format(" Exiting the OrderCheckOutController with order status as [%s]",billDetails .getReturnMessage()));
        return billDetails;
    }


}
