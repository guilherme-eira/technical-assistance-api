package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.business.TechnicianBusiness;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.Technician;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import com.eira.guilherme.technical_assistance.core.service.TechnicianService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TechnicianBusinessImpl implements TechnicianBusiness {

    @Autowired
    TechnicianService service;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Override
    @Transactional
    public Technician createTechnician(Technician technician) {
        technicianAlreadyRegisteredValidation(technician);
        return service.saveTechnician(technician);
    }

    @Override
    public List<Technician> getAllTechnicians() {
        return service.getAllTechnicians();
    }

    @Override
    public Technician getTechnicianById(String id) {
        return service.getTechnicianById(id);
    }

    @Override
    @Transactional
    public Technician updateTechnician(String id, Technician update) {
        technicianAlreadyRegisteredValidation(update);
        var udpatedTechnician = this.getTechnicianById(id).update(update);
        return service.saveTechnician(udpatedTechnician);
    }

    @Override
    @Transactional
    public void deleteTechnician(String id) {
        service.deleteTechnician(id);
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByTechnician(String id) {
        return serviceOrderService.getServiceOrdersByTechnician(id);
    }

    private void technicianAlreadyRegisteredValidation(Technician technician){
        if (service.nameAlreadyInUse(technician.getName())){
            throw new BusinessException("Este nome já está cadastrado");
        } if (service.phoneAlreadyInUse(technician.getPhone())){
            throw new BusinessException("Este telefone já está cadastrado");
        } if (service.emailAlreadyInUse(technician.getEmail())){
            throw new BusinessException("Este email já está cadastrado");
        }
    }
}
