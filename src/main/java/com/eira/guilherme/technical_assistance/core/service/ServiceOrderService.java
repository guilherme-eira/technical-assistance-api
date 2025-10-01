package com.eira.guilherme.technical_assistance.core.service;

import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrderTableVO;
import com.eira.guilherme.technical_assistance.core.domain.Technician;

import java.util.List;

public interface ServiceOrderService {
    ServiceOrder saveServiceOrder(ServiceOrder serviceOrder);
    List<ServiceOrder> getAllServiceOrders();
    ServiceOrder getServiceOrderById(String id);
    void deleteServiceOrder(String id);
    List<ServiceOrderTableVO> getServiceOrdersForTable();
    List<ServiceOrderTableVO> getServiceOrdersForTableByCustomerName(String name);

    Integer countServiceOrdersByTechnician(Technician technician);
    List<ServiceOrder> getServiceOrdersByCustomer(String id);
    List<ServiceOrder> getServiceOrdersByEquipment(String id);
    List<ServiceOrder> getServiceOrdersByService(String id);
    List<ServiceOrder> getServiceOrdersByTechnician(String id);
}
