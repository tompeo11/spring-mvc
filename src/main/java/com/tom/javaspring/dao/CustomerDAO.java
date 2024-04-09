package com.tom.javaspring.dao;

import com.tom.javaspring.dto.CustomerParams;
import com.tom.javaspring.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomers(CustomerParams customerParams);

    int getCustomerCount(CustomerParams customerParams);

    void saveCustomer(Customer theCustomer);

    Customer getById(int id);

    List<Customer> searchCustomer(String theSearchName);

    void deleteCustomer(int id);
}
