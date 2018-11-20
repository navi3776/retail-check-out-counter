package com.shopping_cart.service;

import com.shopping_cart.domain.BillDetails;
import com.shopping_cart.domain.CheckOutOrderDetails;
import com.shopping_cart.domain.CheckOutProduct;
import com.shopping_cart.domain.Product;
import com.shopping_cart.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void updateProductsInventory(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    @Override
    public void setProductsLeft(CheckOutProduct checkOutProduct, Product product) {
        product.setUnitsInInventory(product.getUnitsInInventory() - checkOutProduct.getUnitsOrdered());
    }

    @Override
    public boolean isProductOrderedWithValidQuantity(CheckOutProduct checkOutProduct, Product product, BillDetails billDetails) {
        if (product.getUnitsInInventory() < checkOutProduct.getUnitsOrdered()) {
            logger.info(" Product : " + product.getProductId() + " is not available, kindly modify your orders " +
                    " Currently available number is : " + product.getUnitsInInventory());
            billDetails.setReturnMessage(" One of the products quantity exceeds the quantity available. ");
            return false;
        } else if (checkOutProduct.getUnitsOrdered() == 0) {
            logger.info(" Product : " + product.getProductId() + " has been ordered with quantity 0, please select a quantity or " +
                    " remove the product from the list ");
            billDetails.setReturnMessage(" One of the products quantity is 0, please select a quantity greater than 0 or modify the order");
            return false;
        }
        return true;
    }

    @Override
    public Map<Long, Product> getProductDetailsForProductIds(CheckOutOrderDetails checkOutOrderDetails) {
        return productRepository.findAllById(getProductIds(checkOutOrderDetails.getCheckOutProducts()))
                .stream()
                .collect(Collectors.toMap(product -> product.getProductId(), product -> product));
    }

    @Override
    public Iterable<Long> getProductIds(List<CheckOutProduct> checkOutProducts) {
        return checkOutProducts.stream()
                .map(product -> product.getProductId())
                .collect(Collectors.toList());
    }
}
