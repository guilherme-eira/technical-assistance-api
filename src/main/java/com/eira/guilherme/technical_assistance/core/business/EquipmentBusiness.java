package com.eira.guilherme.technical_assistance.core.business;

import com.eira.guilherme.technical_assistance.core.domain.Equipment;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EquipmentBusiness {
    Equipment createEquipment(Equipment equipment);
    List<Equipment> getAllEquipments();
    Equipment getEquipmentById(String id);
    Equipment updateEquipment(String id, Equipment update);
    void deleteEquipment(String id);
    List<ServiceOrder> getServiceOrdersByEquipment(String id);
}
