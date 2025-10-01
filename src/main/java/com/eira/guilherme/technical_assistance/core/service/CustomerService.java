package com.eira.guilherme.technical_assistance.core.service;

import com.eira.guilherme.technical_assistance.core.domain.Customer;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(String id);
    void deleteCustomer(String id);
    Boolean nameAlreadyInUse(String name);
    Boolean phoneAlreadyInUse(String phone);
    Boolean emailAlreadyInUse(String email);
    Boolean documentAlreadyInUse(String document);
}
