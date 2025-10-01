package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.core.business.ServiceBusiness;
import com.eira.guilherme.technical_assistance.core.domain.Service;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import com.eira.guilherme.technical_assistance.core.service.ServiceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceBusinessImpl implements ServiceBusiness {

    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Override
    @Transactional
    public Service createService(Service service) {
        return serviceService.saveService(service);
    }

    @Override
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @Override
    public Service getServiceById(String id) {
        return serviceService.getServiceById(id);
    }

    @Override
    @Transactional
    public Service updateService(String id, Service update) {
        var updatedService = this.getServiceById(id).update(update);
        return serviceService.saveService(updatedService);
    }

    @Override
    @Transactional
    public void deleteService(String id) {
        serviceService.deleteService(id);
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByService(String id) {
        return serviceOrderService.getServiceOrdersByService(id);
    }
}
