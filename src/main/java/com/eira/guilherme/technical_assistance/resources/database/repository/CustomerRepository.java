package com.eira.guilherme.technical_assistance.resources.database.repository;

import com.eira.guilherme.technical_assistance.resources.database.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    Boolean existsByName(String name);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByDocument(String document);
}
