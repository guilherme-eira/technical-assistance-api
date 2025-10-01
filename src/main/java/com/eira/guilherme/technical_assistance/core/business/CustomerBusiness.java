package com.eira.guilherme.technical_assistance.core.business;

import com.eira.guilherme.technical_assistance.core.domain.Customer;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;

import java.util.List;

public interface CustomerBusiness {
    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(String id);
    Customer updateCustomer(String id, Customer update);
    void deleteCustomer(String id);
    List<ServiceOrder> getServiceOrdersByCustomer(String id);
}
