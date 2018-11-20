package com.shopping_cart.controller;

import com.shopping_cart.domain.*;
import com.shopping_cart.dto.CheckOutOrderDetails;
import com.shopping_cart.dto.OrderedProduct;
import com.shopping_cart.repositories.CategoryRepository;
import com.shopping_cart.repositories.ProductCategoryXrefRepository;
import com.shopping_cart.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utils.TestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationalTests {

    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductCategoryXrefRepository productCategoryXrefRepository;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void invokeCheckOutOrder() throws Exception {
        mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderDetailsWithNonRegisteredUser())))
                .andExpect(status().isOk());

    }

    @Test
    public void invokeOrderCheckOutControllerWithNoProducts() throws Exception {
        mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderDetailsWithNoProducts())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").value(" No products found for the order."));
    }

    @Test
    public void invokeOrderCheckOutControllerWithInvalidProducts() throws Exception {
        mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderDetailsWithInvalidProducts())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").value("Following list of the product/product's [1010] are not available"));
    }

    @Test
    public void invokeOrderCheckOutControllerWithInValidProductQuantity() throws Exception {
        mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderDetailsWithInValidProductQuantity())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").value(" One of the products quantity exceeds the quantity available."));
    }


    @Test
    public void invokeOrderCheckOutControllerWithZeroProductQuantity() throws Exception {
        mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderDetailsWithZeroProductQuantity())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").value(" One of the products quantity is 0."));
    }

    @Test
    public void invokeOrderCheckOutControllerWithOneValidAndOneWithQuantityGreaterThan() throws Exception {
        mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderDetailsOneValidAndOneWithQuantityGreaterThan())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnMessage").value(" One of the products quantity exceeds the quantity available."));
    }

    @Test
    public void invokeCheckOutOrderWithNonRegisteredUser() throws Exception {
        mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderDetailsWithNonRegisteredUser())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerType").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerType").
                        value(CustomerType.NON_REGISTERED_CUSTOMER.name()));
    }

    @Test
    public void createOrderSuccessfully() throws Exception {
    String result =   mockMvc.perform(post("/checkOutOrder/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        TestUtil.convertObjectToJsonBytes(prepareDummyOrderForASuccessfullOrder())))
                .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.customerType").value(CustomerType.NON_REGISTERED_CUSTOMER.name()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currencyType").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.currencyType").value(CurrencyType.INR.toString()))
            .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(result);
    }


    private CheckOutOrderDetails prepareDummyOrderDetailsWithZeroProductQuantity() {

        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1001l).withQuantity(0).build());
        CheckOutOrderDetails orderDetails = new CheckOutOrderDetails();
        orderDetails.setCustomerId(101l);
        orderDetails.setOrderedProducts(orderedProducts);
        return orderDetails;
    }

    private CheckOutOrderDetails prepareDummyOrderDetailsWithInValidProductQuantity() {
        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1001l).withQuantity(190).build());
        CheckOutOrderDetails orderDetails = new CheckOutOrderDetails();
        orderDetails.setCustomerId(101l);
        orderDetails.setOrderedProducts(orderedProducts);

        return orderDetails;
    }


    private CheckOutOrderDetails prepareDummyOrderDetailsOneValidAndOneWithQuantityGreaterThan() {

        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1001l).withQuantity(5).build());
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1002l).withQuantity(200).build());
        CheckOutOrderDetails orderDetails = new CheckOutOrderDetails();
        orderDetails.setCustomerId(101l);
        orderDetails.setOrderedProducts(orderedProducts);
        return orderDetails;
    }


    private CheckOutOrderDetails prepareDummyOrderDetailsWithNoProducts() {

        List<OrderedProduct> orderedProducts = new ArrayList<>();
        CheckOutOrderDetails orderDetails = new CheckOutOrderDetails();
        orderDetails.setCustomerId(101l);
        orderDetails.setOrderedProducts(orderedProducts);
        return orderDetails;
    }

    private CheckOutOrderDetails prepareDummyOrderDetailsWithInvalidProducts() {


        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1010l).withQuantity(2).build());
        CheckOutOrderDetails orderDetails = new CheckOutOrderDetails();
        orderDetails.setCustomerId(101l);
        orderDetails.setOrderedProducts(orderedProducts);

        return orderDetails;
    }


    private CheckOutOrderDetails prepareDummyOrderDetailsWithNonRegisteredUser() {


        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1001l).withQuantity(2).build());
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1002l).withQuantity(3).build());
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1003l).withQuantity(4).build());
        CheckOutOrderDetails orderDetails = new CheckOutOrderDetails();
        orderDetails.setCustomerId(101l);
        orderDetails.setOrderedProducts(orderedProducts);
        return orderDetails;
    }

    private CheckOutOrderDetails prepareDummyOrderForASuccessfullOrder() {

        List<OrderedProduct> orderedProducts = new ArrayList<>();
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1001l).withQuantity(2).build());
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1002l).withQuantity(3).build());
        orderedProducts.add(new OrderedProduct.OrderedProductBuilder(1003l).withQuantity(4).build());
        CheckOutOrderDetails orderDetails = new CheckOutOrderDetails();
        orderDetails.setCustomerId(101l);
        orderDetails.setOrderedProducts(orderedProducts);
        return orderDetails;
    }


}
