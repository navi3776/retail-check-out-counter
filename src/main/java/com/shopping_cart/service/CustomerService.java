package com.shopping_cart.service;

import com.shopping_cart.domain.BillDetails;
import com.shopping_cart.domain.Customer;

public interface CustomerService {

    Customer findCustomerByCustomerId(long customerId);

    void populateCustomerDetailsForBillDetails(BillDetails billDetails, Customer customer);

}
