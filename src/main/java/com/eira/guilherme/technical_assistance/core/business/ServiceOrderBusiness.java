package com.eira.guilherme.technical_assistance.core.business;

import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderTableDTO;

import java.util.List;

public interface ServiceOrderBusiness {
    ServiceOrder createServiceOrder(ServiceOrder serviceOrder);
    List<ServiceOrder> getAllServiceOrders();
    ServiceOrder getServiceOrderById(String id);
    List<ServiceOrderTableDTO> getServiceOrdersForTable();
    List<ServiceOrderTableDTO> getServiceOrdersForTableByCustomerName(String name);
    ServiceOrder updateServiceOrder(String id, ServiceOrder update);
    void deleteServiceOrder(String id);
    void start(String id);
    void cancel(String id);
    void complete(String id);
}
