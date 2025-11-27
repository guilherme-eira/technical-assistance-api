package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.business.ServiceOrderBusiness;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;
import com.eira.guilherme.technical_assistance.core.service.EquipmentService;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import com.eira.guilherme.technical_assistance.core.service.ServiceService;
import com.eira.guilherme.technical_assistance.core.service.TechnicianService;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderTableDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ServiceOrderBusinessImpl implements ServiceOrderBusiness {

    @Autowired
    ServiceOrderService service;

    @Autowired
    TechnicianService technicianService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    EquipmentService equipmentService;

    @Override
    @Transactional
    public ServiceOrder createServiceOrder(ServiceOrder serviceOrder) {
        estimatedDeliveryDateIsAfterEntryDateValidation(serviceOrder, null);
        serviceEquipmentCompatibilityValidation(serviceOrder);
        return service.saveServiceOrder(serviceOrder);
    }

    @Override
    public List<ServiceOrder> getAllServiceOrders() {
        return service.getAllServiceOrders();
    }

    @Override
    public ServiceOrder getServiceOrderById(String id) {
        return service.getServiceOrderById(id);
    }

    @Override
    public List<ServiceOrderTableDTO> getServiceOrdersForTable() {
        return service.getServiceOrdersForTable();
    }

    @Override
    public List<ServiceOrderTableDTO> getServiceOrdersForTableByCustomerName(String name) {
        return service.getServiceOrdersForTableByCustomerName(name);
    }

    @Override
    @Transactional
    public ServiceOrder updateServiceOrder(String id, ServiceOrder update) {
        var serviceOrderToBeUpdated = this.getServiceOrderById(id);
        estimatedDeliveryDateIsAfterEntryDateValidation(serviceOrderToBeUpdated, update);
        serviceOrderAlreadyFinishedValidation(serviceOrderToBeUpdated);
        var updatedServiceOrder = serviceOrderToBeUpdated.update(update);
        return service.saveServiceOrder(updatedServiceOrder);
    }

    @Override
    @Transactional
    public void deleteServiceOrder(String id) {
        service.deleteServiceOrder(id);
    }

    @Override
    @Transactional
    public void start(String id) {
        var serviceOrderToBeStarted = service.getServiceOrderById(id);
        technicianMaxOngoingServicesValidation(serviceOrderToBeStarted);
        serviceOrderNotOpenValidation(serviceOrderToBeStarted);
        serviceOrderToBeStarted.start();
        service.saveServiceOrder(serviceOrderToBeStarted);
    }

    @Override
    @Transactional
    public void cancel(String id) {
        var serviceOrderToBeCanceled = service.getServiceOrderById(id);
        serviceOrderAlreadyFinishedValidation(serviceOrderToBeCanceled);
        serviceOrderToBeCanceled.cancel();
        service.saveServiceOrder(serviceOrderToBeCanceled);
    }

    @Override
    @Transactional
    public void complete(String id) {
        var serviceOrderToBeCompleted = service.getServiceOrderById(id);
        serviceOrderNotInProgressValidation(serviceOrderToBeCompleted);
        serviceOrderToBeCompleted.complete();
        service.saveServiceOrder(serviceOrderToBeCompleted);
    }

    private void serviceEquipmentCompatibilityValidation(ServiceOrder serviceOrder){
        var equipment = equipmentService.getEquipmentById(serviceOrder.getEquipmentId());
        var serviceList = serviceOrder.getServiceIdList().stream().map(s -> serviceService.getServiceById(s)).toList();

        if (serviceList.stream().anyMatch(s -> !s.getEquipmentType().equals(equipment.getType()))){
            throw new BusinessException("É necessário que os serviços escolhidos sejam compatíveis com o tipo do equipamento");
        }
    }

    private void technicianMaxOngoingServicesValidation(ServiceOrder serviceOrder){
        var technician = technicianService.getTechnicianById(serviceOrder.getTechnicianId());
        var serviceOrdersInProgress = service.countServiceOrdersByTechnician(technician);
        if (serviceOrdersInProgress > 3){
            throw new BusinessException("Não é possível atribuir uma nova ordem de serviço a este técnico, pois ele já possui 3 em andamento");
        }
    }

    private void serviceOrderAlreadyFinishedValidation(ServiceOrder serviceOrder){
        if (serviceOrder.getStatus() == ServiceOrderStatus.COMPLETED || serviceOrder.getStatus() == ServiceOrderStatus.CANCELED){
            throw new BusinessException("Esta ordem de serviço já foi finalizada");
        }
    }

    private void serviceOrderNotOpenValidation(ServiceOrder serviceOrder){
        if (serviceOrder.getStatus() != ServiceOrderStatus.OPEN){
            throw new BusinessException("A ordem de serviço precisa estar aberta para ser iniciada");
        }
    }

    private void serviceOrderNotInProgressValidation(ServiceOrder serviceOrder){
        if (serviceOrder.getStatus() != ServiceOrderStatus.IN_PROGRESS){
            throw new BusinessException("A ordem de serviço precisa estar em progresso para ser finalizada");
        }
    }

    private void estimatedDeliveryDateIsAfterEntryDateValidation(ServiceOrder serviceOrder, ServiceOrder update){
        LocalDate estimated = update != null ? update.getEstimatedDeliveryDate() : serviceOrder.getEstimatedDeliveryDate();
        if (estimated != null && serviceOrder.getEntryDate().isAfter(estimated)) {
            throw new BusinessException("A data estimada de entrega deve ser posterior a data de entrada");
        }
    }
}
