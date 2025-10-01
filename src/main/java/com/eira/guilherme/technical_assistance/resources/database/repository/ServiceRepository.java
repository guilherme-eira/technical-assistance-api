package com.eira.guilherme.technical_assistance.resources.database.repository;

import com.eira.guilherme.technical_assistance.resources.database.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
}
