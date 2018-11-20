package com.shopping_cart.service;

import com.shopping_cart.domain.Orders;
import com.shopping_cart.repositories.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void saveOrder(Orders orders) {
        orderRepository.save(orders);
        logger.info(String.format(" Order created with order Id  [%s]", orders.getOrderId()));
    }
}
