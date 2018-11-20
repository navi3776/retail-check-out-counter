package com.shopping_cart.service;

import com.shopping_cart.controller.OrderCheckOutController;
import com.shopping_cart.domain.Product;
import com.shopping_cart.dto.BillDetails;
import com.shopping_cart.dto.CheckOutOrderDetails;
import com.shopping_cart.dto.OrderedProduct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class OrderDetailsValidatorImpl implements OrderDetailsValidator {

    private Logger logger = LogManager.getLogger(OrderCheckOutController.class);

    @Override
    public boolean checkIfOrderDetailsAreNullOrEmpty(CheckOutOrderDetails checkOutOrderDetails, BillDetails billDetails) {
        if (ObjectUtils.isEmpty(checkOutOrderDetails)) {
            logger.info(String.format(" Order details are null/Empty."));
            billDetails.setReturnMessage(" Order details are null/Empty.");
            billDetails.setOrderSuccessful(false);
            return true;
        } else if (CollectionUtils.isEmpty(checkOutOrderDetails.getOrderedProducts())) {
            logger.info(String.format(" No products found for the order "));
            billDetails.setReturnMessage(" No products found for the order.");
            billDetails.setOrderSuccessful(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean isProductOrderedWithValidQuantity(OrderedProduct orderedProduct, Product product, BillDetails billDetails) {
        if (product.getUnitsInInventory() < orderedProduct.getUnitsOrdered()) {
            logger.info(" Product : " + product.getProductId() + " is not available, kindly modify your orders " +
                    " Currently available number is : " + product.getUnitsInInventory());
            billDetails.setReturnMessage(" One of the products quantity exceeds the quantity available.");
            orderedProduct.setOrderMessage(" Quantity Exceeds available quantity ");
            return false;
        } else if (orderedProduct.getUnitsOrdered() == 0) {
            logger.info(" Product : " + product.getProductId() + " has been ordered with quantity 0, please select a quantity or " +
                    " remove the product from the list ");
            billDetails.setReturnMessage(" One of the products quantity is 0.");
            orderedProduct.setOrderMessage(" Quantity is 0.");
            return false;
        }
        orderedProduct.setOrderMessage(" Product has been ordered Successfully.");
        return true;
    }
}
