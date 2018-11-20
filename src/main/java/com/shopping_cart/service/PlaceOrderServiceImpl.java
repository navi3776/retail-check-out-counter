package com.shopping_cart.service;

import com.shopping_cart.controller.OrderCheckOutController;
import com.shopping_cart.domain.*;
import com.shopping_cart.dto.BillDetails;
import com.shopping_cart.dto.CheckOutOrderDetails;
import com.shopping_cart.dto.OrderedProduct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlaceOrderServiceImpl implements PlaceOrderService {

    private Logger logger = LogManager.getLogger(OrderCheckOutController.class);

    public static final String BILL_DETAIL_DATE_FORMAT = "dd/MM/yyyy";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductXrefService orderProductXrefService;

    @Autowired
    private OrderDetailsValidator orderDetailsValidator;

    @Override
    public BillDetails placeOrder(CheckOutOrderDetails checkOutOrderDetails) {

        BillDetails billDetails = new BillDetails();

        /*
        * Validation for Empty Orders.
        * */
        if (orderDetailsValidator.checkIfOrderDetailsAreNullOrEmpty(checkOutOrderDetails, billDetails)) {
            return billDetails;
        }

        /*
        * Populating the details for customer for current order.
        * If Customer is not found in database, create a new one as 'Non_registered_Customer'
        * */
        populateCustomerDetailsForBillDetails(billDetails, checkOutOrderDetails.getCustomerId());

        List<OrderedProduct> orderedProducts = checkOutOrderDetails.getOrderedProducts();

        try{
            Map<Long, Product> products = productService.getProductDetailsForOrderedProductIds(getProductIds(orderedProducts));
            List<OrderedProduct> productsNotFoundInInventory = findProductsNotAvailableInInventory(orderedProducts, products);

            if (CollectionUtils.isEmpty(productsNotFoundInInventory)) {
                logger.info(" All Ordered Products are available in the inventory, hence proceeding ahead.");
                List<Product> productListForOrder = processOrderedProductsAndCalculateCostAndTaxes(orderedProducts, products, billDetails);
                if (!CollectionUtils.isEmpty(productListForOrder)) {
                    Orders orders = persistOrder(productListForOrder);
                    updateBillDetails(billDetails, orders);
                }
            } else {
                String productsNotFoundInInventoryString = convertListToDelimitedString(productsNotFoundInInventory);
                logger.info(String.format(" Following list of the product/product's [%s] are not available for current order", productsNotFoundInInventoryString));
                billDetails.setReturnMessage(String.format(" Following list of the product/product's [%s] are not available", productsNotFoundInInventoryString));
            }
        }catch(Exception exception){
            logger.info(" Exception occurred while placing order "+exception);
            throw new RuntimeException();
        }
        return billDetails;
    }

    private void setProductsLeft(OrderedProduct orderedProduct, Product product) {
        product.setUnitsInInventory(product.getUnitsInInventory() - orderedProduct.getUnitsOrdered());
    }

    private void populateCustomerDetailsForBillDetails(BillDetails billDetails, long customerId) {
        Customer customer;
        try{
            customer = customerService.findCustomerByCustomerId(customerId);
            if (Objects.isNull(customer)) {
                customer = new Customer.CustomerBuilder()
                        .withCustomerType(CustomerType.NON_REGISTERED_CUSTOMER)
                        .build();
                customerService.saveCustomer(customer);
            }
            logger.info(String.format(" Customer Type for the current order is [%s] ", customer.getCustomerType()));

        }catch(Exception e){
            logger.error(" Error creating/updating new customer "+e.getCause());
            throw new RuntimeException();
        }
        updateBillDetailsWithCustomerDetails(billDetails, customer);
    }

    private void updateBillDetailsWithCustomerDetails(BillDetails billDetails, Customer customer) {
        billDetails.setCustomerId(customer.getCustomerId());
        billDetails.setCustomerType(customer.getCustomerType());
    }


    private Iterable<Long> getProductIds(List<OrderedProduct> orderedProducts) {
        return orderedProducts.stream()
                .map(product -> product.getProductId())
                .collect(Collectors.toList());
    }

    private Orders persistOrder(List<Product> productList) {
        Orders orders = new Orders();
        orders.setProducts(productList);
        productService.updateProductsInventory(productList);
        orderService.saveOrder(orders);
        orderProductXrefService.saveOrderProductXref(orders, productList);
        return orders;
    }

    private String convertListToDelimitedString(List<OrderedProduct> productsNotFoundInInventory) {
        return productsNotFoundInInventory
                .stream()
                .map(orderedProduct -> String.valueOf(orderedProduct.getProductId()))
                .collect(Collectors.joining(","));
    }

    private List<OrderedProduct> findProductsNotAvailableInInventory(List<OrderedProduct> orderedProducts, Map<Long, Product> productsInInventoryMap) {
        List<OrderedProduct> productsNotFoundInInventory = new ArrayList<>();
        orderedProducts.forEach(orderedProduct -> {
            if (productsInInventoryMap.get(orderedProduct.getProductId()) == null) {
                productsNotFoundInInventory.add(orderedProduct);
            }
        });
        return productsNotFoundInInventory;
    }

    private List<Product> processOrderedProductsAndCalculateCostAndTaxes(List<OrderedProduct> orderedProducts, Map<Long, Product> products, BillDetails billDetails) {
        List<Product> productList = new ArrayList<>();
        try{
            for (OrderedProduct orderedProduct : orderedProducts) {
                Product product = products.get(orderedProduct.getProductId());
                if (!orderDetailsValidator.isProductOrderedWithValidQuantity(orderedProduct, product, billDetails)) {
                    logger.info(" Product found with invalid Quantity. ");
                    updateBillDetailsForInvalidProductDetails(orderedProducts, billDetails, productList);
                    break;
                } else {
                    logger.info(String.format("Calculating the cost and taxes for the ordered product [%s]", product.getProductName()));
                    calculateTotalCostAndTaxesForOrderedProduct(orderedProduct, product);
                    updateTotalCostAndTaxesForAllOrderedProducts(billDetails, orderedProduct);
                    setProductsLeft(orderedProduct, product);
                    updateBillDetailsForValidProducts(orderedProducts, billDetails);
                    productList.add(product);
                }
            }
        }catch(Exception exception){
            logger.info(" Exception occurred processOrderedProductsAndCalculateCostAndTaxes "+exception);
            throw new RuntimeException();
        }

        return productList;
    }

    private void calculateTotalCostAndTaxesForOrderedProduct(OrderedProduct orderedProduct, Product product) {
        double costPrice = orderedProduct.getUnitsOrdered() * product.getPrice();
        double taxes = calculateTaxesForOrderedProduct(costPrice, product.getCategory());
        orderedProduct.setApplicableTaxes(taxes);
        orderedProduct.setTotalCost(costPrice + taxes);
    }

    /*
    * Using a HashMap for <Type,TaxValue>, in case we number of different of
    * criteria to calculate tax.
    * */
    private double calculateTaxesForOrderedProduct(double totalCost,TaxCategory taxCategory) {
        return  taxCategory.getValue() * (totalCost / 100);
    }

    private void resetCostAndTaxValue(List<OrderedProduct> orderedProducts, BillDetails billDetails) {
        orderedProducts.forEach(orderedProduct -> {
            orderedProduct.setApplicableTaxes(0);
            orderedProduct.setTotalCost(0);
            orderedProduct.setOrderMessage("");
        });
        billDetails.setTotalSalesTax(0.0);
        billDetails.setTotalCost(0.0);
    }

    private void updateTotalCostAndTaxesForAllOrderedProducts(BillDetails billDetails, OrderedProduct orderedProduct) {
        billDetails.setTotalCost(orderedProduct.getTotalCost() + billDetails.getTotalCost());
        billDetails.setTotalSalesTax(orderedProduct.getApplicableTaxes() + billDetails.getTotalSalesTax());
    }


    private void updateBillDetailsForValidProducts(List<OrderedProduct> orderedProducts, BillDetails billDetails) {
        billDetails.setOrderSuccessful(true);
        billDetails.setSuccessfulOrderedProducts(orderedProducts);
    }

    private void updateBillDetailsForInvalidProductDetails(List<OrderedProduct> orderedProducts, BillDetails billDetails, List<Product> productList) {
        productList.clear();
        resetCostAndTaxValue(orderedProducts, billDetails);
        billDetails.setOrderSuccessful(false);
        billDetails.setFailedOrderedProducts(orderedProducts);
    }

    private void updateBillDetails(BillDetails billDetails, Orders orders) {
        billDetails.setOrderId(orders.getOrderId());
        billDetails.setCurrencyType(CurrencyType.INR);
        billDetails.setReturnMessage(" Orders created successfully with orders id : " + billDetails.getOrderId());
        billDetails.setBillingDate(getLocalDate());
    }

    private String getLocalDate() {
        LocalDateTime localDate = LocalDateTime.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BILL_DETAIL_DATE_FORMAT);
        return localDate.format(formatter);
    }


}
