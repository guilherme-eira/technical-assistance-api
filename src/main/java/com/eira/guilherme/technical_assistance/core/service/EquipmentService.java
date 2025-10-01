package com.eira.guilherme.technical_assistance.core.service;

import com.eira.guilherme.technical_assistance.core.domain.Equipment;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;

import java.util.List;

public interface EquipmentService {
    Equipment saveEquipment(Equipment equipment);
    List<Equipment> getAllEquipments();
    Equipment getEquipmentById(String id);
    void deleteEquipment(String id);
    Boolean serialNumberAlreadyInUse(String serialNumber);
}
