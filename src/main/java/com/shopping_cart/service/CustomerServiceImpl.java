package com.shopping_cart.service;

import com.shopping_cart.domain.Customer;
import com.shopping_cart.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findCustomerByCustomerId(long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
