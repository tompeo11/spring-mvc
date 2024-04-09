package com.tom.javaspring.service;

import com.tom.javaspring.dto.CustomerParams;
import com.tom.javaspring.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers(CustomerParams customerParams);

    int getCustomerCount(CustomerParams customerParams);

    void saveCustomer(Customer theCustomer);

    Customer getById(int id);

    List<Customer> searchCustomer(String theSearchName);

    void deleteCustomer(int id);
}
