package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.business.CustomerBusiness;
import com.eira.guilherme.technical_assistance.core.domain.Customer;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.service.CustomerService;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerBusinessImpl implements CustomerBusiness {

    @Autowired
    CustomerService service;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        customerAlreadyRegisteredValidation(customer);
        return service.saveCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(String id) {
        return service.getCustomerById(id);
    }

    @Override
    @Transactional
    public Customer updateCustomer(String id, Customer update) {
        customerAlreadyRegisteredValidation(update);
        var updatedCustomer = this.getCustomerById(id).updateCustomer(update);
        return service.saveCustomer(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(String id) {
        service.deleteCustomer(id);
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByCustomer(String id) {
        return serviceOrderService.getServiceOrdersByCustomer(id);
    }

    private void customerAlreadyRegisteredValidation(Customer customer){
        if (service.nameAlreadyInUse(customer.getName())){
            throw new BusinessException("Este nome já está cadastrado");
        }if (service.phoneAlreadyInUse(customer.getPhone())){
            throw new BusinessException("Este telefone já está cadastrado");
        }if (service.emailAlreadyInUse(customer.getEmail())){
            throw new BusinessException("Este email já está cadastrado");
        }if (service.documentAlreadyInUse(customer.getDocument())){
            throw new BusinessException("Este documento já está cadastrado");
        }
    }

}
