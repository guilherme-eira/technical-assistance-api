package com.eira.guilherme.technical_assistance.resources.database.repository;

import com.eira.guilherme.technical_assistance.resources.database.entity.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TechnicianRepository extends JpaRepository<TechnicianEntity, String> {
    Boolean existsByName(String name);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
}
