package com.eira.guilherme.technical_assistance.core.business;

import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.Technician;

import java.util.List;

public interface TechnicianBusiness {
    Technician createTechnician(Technician technician);
    List<Technician> getAllTechnicians();
    Technician getTechnicianById(String id);
    Technician updateTechnician(String id, Technician update);
    void deleteTechnician(String id);
    List<ServiceOrder> getServiceOrdersByTechnician(String id);
}
