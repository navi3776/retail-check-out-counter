package com.shopping_cart.service;

import com.shopping_cart.domain.Product;
import com.shopping_cart.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void updateProductsInventory(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    @Override
    public Map<Long, Product> getProductDetailsForOrderedProductIds(Iterable<Long> productIds) {
        return productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(product -> product.getProductId(), product -> product));
    }

}
