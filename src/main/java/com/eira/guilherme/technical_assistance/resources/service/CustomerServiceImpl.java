package com.eira.guilherme.technical_assistance.resources.service;

import com.eira.guilherme.technical_assistance.commom.exception.ResourceNotFoundException;
import com.eira.guilherme.technical_assistance.commom.mapper.CustomerMapper;
import com.eira.guilherme.technical_assistance.commom.mapper.ServiceOrderMapper;
import com.eira.guilherme.technical_assistance.core.domain.Customer;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.resources.database.repository.CustomerRepository;
import com.eira.guilherme.technical_assistance.core.service.CustomerService;
import com.eira.guilherme.technical_assistance.resources.database.repository.ServiceOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository repository;

    @Autowired
    CustomerMapper mapper;

    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return mapper.toDomain(repository.save(mapper.toEntity(customer)));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll().stream().map(e -> mapper.toDomain(e)).toList();
    }

    @Override
    public Customer getCustomerById(String id) {
        var customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum cliente com este id"));
        return mapper.toDomain(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        var customerToBeDeleted = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum cliente com este id"));
        serviceOrderRepository.deleteByCustomer(customerToBeDeleted);
        repository.delete(customerToBeDeleted);
    }

    @Override
    public Boolean nameAlreadyInUse(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Boolean phoneAlreadyInUse(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public Boolean emailAlreadyInUse(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Boolean documentAlreadyInUse(String document) {
        return repository.existsByDocument(document);
    }

}
