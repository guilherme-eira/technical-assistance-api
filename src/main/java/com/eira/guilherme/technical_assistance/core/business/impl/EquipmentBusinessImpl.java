package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.business.EquipmentBusiness;
import com.eira.guilherme.technical_assistance.core.domain.Equipment;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.service.EquipmentService;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EquipmentBusinessImpl implements EquipmentBusiness {

    @Autowired
    EquipmentService service;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Override
    @Transactional
    public Equipment createEquipment(Equipment equipment) {
        equipmentAlreadyRegisteredValidation(equipment);
        return service.saveEquipment(equipment);
    }

    @Override
    public List<Equipment> getAllEquipments() {
        return service.getAllEquipments();
    }

    @Override
    public Equipment getEquipmentById(String id) {
        return service.getEquipmentById(id);
    }

    @Override
    @Transactional
    public Equipment updateEquipment(String id, Equipment update) {
        equipmentAlreadyRegisteredValidation(update);
        var updatedEquipment = this.getEquipmentById(id).update(update);
        return service.saveEquipment(updatedEquipment);
    }

    @Override
    @Transactional
    public void deleteEquipment(String id) {
        service.deleteEquipment(id);
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByEquipment(String id) {
        return serviceOrderService.getServiceOrdersByEquipment(id);
    }

    private void equipmentAlreadyRegisteredValidation(Equipment equipment){
        if (service.serialNumberAlreadyInUse(equipment.getSerialNumber())){
            throw new BusinessException("Este número de série já está cadastrado");
        }
    }
}
