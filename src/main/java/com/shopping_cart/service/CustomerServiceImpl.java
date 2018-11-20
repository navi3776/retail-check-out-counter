package com.shopping_cart.service;

import com.shopping_cart.domain.BillDetails;
import com.shopping_cart.domain.Customer;
import com.shopping_cart.domain.CustomerType;
import com.shopping_cart.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findCustomerByCustomerId(long customerId) {
        return populateCustomer(customerRepository.findById(customerId).orElse(null));
    }

    private Customer populateCustomer(Customer customer) {
        if (Objects.isNull(customer)) {
            customer = new Customer.CustomerBuilder()
                    .withCustomerType(CustomerType.NON_REGISTERED_CUSTOMER)
                    .build();
            customerRepository.save(customer);
        }
        logger.info(String.format(" Customer Type for the current order is [%s] ", customer.getCustomerType()));
        return customer;
    }

    @Override
    public void populateCustomerDetailsForBillDetails(BillDetails billDetails, Customer customer) {
        billDetails.setCustomerId(customer.getCustomerId());
        billDetails.setCustomerType(customer.getCustomerType());
    }
}
