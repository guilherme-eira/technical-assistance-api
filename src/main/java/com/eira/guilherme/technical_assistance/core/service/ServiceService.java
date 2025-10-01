package com.eira.guilherme.technical_assistance.core.service;

import com.eira.guilherme.technical_assistance.core.domain.Service;

import java.util.List;

public interface ServiceService {
    Service saveService(Service service);
    List<Service> getAllServices();
    Service getServiceById(String id);
    void deleteService(String id);
}
