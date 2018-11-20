package com.shopping_cart.service;

import com.shopping_cart.domain.OrderProductXref;
import com.shopping_cart.domain.Orders;
import com.shopping_cart.domain.Product;
import com.shopping_cart.repositories.OrderProductXrefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OrderProductXrefServiceImpl implements OrderProductXrefService {

    @Autowired
    private OrderProductXrefRepository orderProductXrefRepository;

    @Override
    public void saveOrderProductXref(Orders orders, List<Product> productList) {

        for (Product product : productList) {
            OrderProductXref orderProductXref = new OrderProductXref();
            orderProductXref.setOrderId(orders.getOrderId());
            orderProductXref.setProductId(product.getProductId());
            orderProductXrefRepository.save(orderProductXref);
        }
    }
}
