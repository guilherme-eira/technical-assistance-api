package com.eira.guilherme.technical_assistance.core.business;

import com.eira.guilherme.technical_assistance.core.domain.Service;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;

import java.util.List;

public interface ServiceBusiness {
    Service createService(Service service);
    List<Service> getAllServices();
    Service getServiceById(String id);
    Service updateService(String id, Service update);
    void deleteService(String id);
    List<ServiceOrder> getServiceOrdersByService(String id);
}
