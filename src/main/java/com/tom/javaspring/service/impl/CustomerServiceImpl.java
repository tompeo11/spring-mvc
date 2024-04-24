package com.tom.javaspring.service.impl;

import com.tom.javaspring.dao.CustomerDAO;
import com.tom.javaspring.dto.CustomerParams;
import com.tom.javaspring.entity.Customer;
import com.tom.javaspring.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    @Transactional
    public List<Customer> getCustomers(CustomerParams customerParams) {return customerDAO.getCustomers(customerParams);}

    @Override
    @Transactional
    public int getCustomerCount(CustomerParams customerParams) {return customerDAO.getCustomerCount(customerParams);}

    @Override
    @Transactional
    public void saveCustomer(Customer theCustomer) {
        customerDAO.saveCustomer(theCustomer);
    }

    @Override
    @Transactional
    public Customer getById(int id) {
        return customerDAO.getById(id);
    }

    @Override
    @Transactional
    public List<Customer> searchCustomer(String theSearchName) {
        return customerDAO.searchCustomer(theSearchName);
    }

    @Override
    @Transactional
    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }
}
