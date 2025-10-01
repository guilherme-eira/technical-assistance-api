package com.eira.guilherme.technical_assistance.core.domain;

import com.eira.guilherme.technical_assistance.core.domain.enums.CustomerType;

import java.util.List;
import java.util.UUID;

public class Customer {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String document;
    private CustomerType type;

    public Customer(String id, String name, String phone, String email, String document, CustomerType type) {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.document = document;
        this.type = type;
    }

    public Customer updateCustomer(Customer customer){
        if (customer.getName() != null){
            this.name = customer.getName();
        } if (customer.getPhone() != null){
            this.phone = customer.getPhone();
        }if (customer.getEmail() != null){
            this.email = customer.getEmail();
        } if (customer.getDocument() != null){
            this.document = customer.getDocument();
        } if (customer.getType() != null) {
            this.type = customer.getType();
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDocument() {
        return document;
    }

    public CustomerType getType() {
        return type;
    }
}
