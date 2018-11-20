package com.shopping_cart.service;

import com.shopping_cart.controller.OrderCheckOutController;
import com.shopping_cart.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CreateOrderServiceImpl implements CreateOrderService {

    private Logger logger = LogManager.getLogger(OrderCheckOutController.class);

    @Autowired
    private CalculateCostAndTaxesService calculateCostAndTaxesService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductXrefService orderProductXrefService;

    @Override
    public BillDetails createOrder(CheckOutOrderDetails checkOutOrderDetails) {

        BillDetails billDetails = new BillDetails();

        if (checkIfOrderDetailsAreNullOrEmpty(checkOutOrderDetails, billDetails)) {
            return billDetails;
        }

        customerService.populateCustomerDetailsForBillDetails(billDetails, customerService.findCustomerByCustomerId(checkOutOrderDetails.getCustomerId()));

        Map<Long, Product> products = productService.getProductDetailsForProductIds(checkOutOrderDetails);
        if (validateProductsWithOrderedProducts(checkOutOrderDetails, products)) {
            List<Product> productList = new ArrayList<>();
            processOrderedProductsAndCalculateCostAndTaxes(checkOutOrderDetails, productList, products, billDetails);
            if (!CollectionUtils.isEmpty(productList)) {
                Orders orders = new Orders();
                orders.setProducts(productList);
                productService.updateProductsInventory(productList);
                orderService.saveOrder(orders);
                orderProductXrefService.saveOrderProductXref(orders, productList);
                updateBillDetails(billDetails, orders);
            }
        } else {
            logger.info(" One of the product/product's are not available, please try after sometime or modify your order ");
            billDetails.setReturnMessage(" One of the product/product's are not available, please try after sometime or modify your order ");
        }
        return billDetails;
    }

    private boolean checkIfOrderDetailsAreNullOrEmpty(CheckOutOrderDetails checkOutOrderDetails, BillDetails billDetails) {
        if (ObjectUtils.isEmpty(checkOutOrderDetails)) {
            logger.info(String.format(" Order details are null/Empty."));
            billDetails.setReturnMessage(" Order details are null/Empty, please add a product to your order ");
            return true;
        } else if (CollectionUtils.isEmpty(checkOutOrderDetails.getCheckOutProducts())) {
            logger.info(String.format(" No products found for the order "));
            billDetails.setReturnMessage(" No products found for the order, please select a product and try again");
            return true;
        }
        return false;
    }

    private boolean validateProductsWithOrderedProducts(CheckOutOrderDetails checkOutOrderDetails, Map<Long, Product> products) {
        return products.size() == checkOutOrderDetails.getCheckOutProducts().size();
    }

    private void updateBillDetails(BillDetails billDetails, Orders orders) {
        billDetails.setOrderId(orders.getOrderId());
        billDetails.setCurrencyType(CurrencyType.INR);
        billDetails.setReturnMessage(" Orders created successfully with orders id : " + billDetails.getOrderId());
    }

    private void processOrderedProductsAndCalculateCostAndTaxes(CheckOutOrderDetails checkOutOrderDetails, List<Product> productList, Map<Long, Product> products, BillDetails billDetails) {
        boolean isSuccess = true;
        for (CheckOutProduct checkOutProduct : checkOutOrderDetails.getCheckOutProducts()) {
            Product product = products.get(checkOutProduct.getProductId());
            if (!productService.isProductOrderedWithValidQuantity(checkOutProduct, product, billDetails)) {
                productList.clear();
                isSuccess = false;
                break;
            } else {
                logger.info(String.format("Calculating the cost and taxes for the ordered product [%s]", product.getProductName()));
                calculateCostAndTaxesService.calculateTotalCostAndTaxesForOrderedProduct(checkOutProduct, product);
                calculateCostAndTaxesService.updateTotalCostAndTaxesForAllOrderedProducts(billDetails, checkOutProduct);
                productService.setProductsLeft(checkOutProduct, product);
                productList.add(product);
            }
        }
        if(!isSuccess){
            resetCostAndTaxValue(checkOutOrderDetails.getCheckOutProducts(),billDetails);
        }
        billDetails.setCheckOutProducts(checkOutOrderDetails.getCheckOutProducts());
    }

    private void resetCostAndTaxValue(List<CheckOutProduct> checkOutProducts,BillDetails billDetails){
        checkOutProducts.forEach(checkOutProduct -> {
            checkOutProduct.setApplicableTaxes(0);
            checkOutProduct.setTotalCost(0);
        });
        billDetails.setTotalSalesTax(0.0);
        billDetails.setTotalCost(0.0);
    }

}
