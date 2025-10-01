package com.eira.guilherme.technical_assistance.resources.database.repository;

import com.eira.guilherme.technical_assistance.resources.database.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity, String> {
    Boolean existsBySerialNumber(String serialNumber);
}
