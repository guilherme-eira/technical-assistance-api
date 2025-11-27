package com.eira.guilherme.technical_assistance.resources.service;

import com.eira.guilherme.technical_assistance.commom.exception.ResourceNotFoundException;
import com.eira.guilherme.technical_assistance.commom.mapper.ServiceOrderMapper;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.Technician;
import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderTableDTO;
import com.eira.guilherme.technical_assistance.resources.database.entity.ServiceEntity;
import com.eira.guilherme.technical_assistance.resources.database.entity.ServiceOrderEntity;
import com.eira.guilherme.technical_assistance.resources.database.entity.TechnicianEntity;
import com.eira.guilherme.technical_assistance.resources.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

    @Autowired
    ServiceOrderRepository repository;

    @Autowired
    ServiceOrderMapper mapper;

    @Autowired
    TechnicianRepository technicianRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public ServiceOrder saveServiceOrder(ServiceOrder serviceOrder) {
        var serviceOrderToBeSaved = buildServiceOrderEntity(serviceOrder);
        var savedServiceOrder = repository.save(serviceOrderToBeSaved);
        return mapper.toDomain(savedServiceOrder);
    }

    @Override
    public List<ServiceOrder> getAllServiceOrders() {
        return repository.findAll().stream().map(s -> mapper.toDomain(s)).toList();
    }

    @Override
    public ServiceOrder getServiceOrderById(String id) {
        var serviceOrder = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhuma ordem de serviço com este id"));
        return mapper.toDomain(serviceOrder);
    }

    @Override
    public void deleteServiceOrder(String id) {
        var serviceOrderToBeDeleted = repository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Não há nenhuma ordem de serviço com este id"));
        repository.delete(serviceOrderToBeDeleted);
    }

    @Override
    public List<ServiceOrderTableDTO> getServiceOrdersForTable() {
        return repository.findAllServiceOrdersForTable();
    }

    @Override
    public List<ServiceOrderTableDTO> getServiceOrdersForTableByCustomerName(String name) {
        return repository.findAllServiceOrdersForTableByCustomerName(name);
    }

    @Override
    public Integer countServiceOrdersByTechnician(Technician technician) {
        var technicianEntity = new TechnicianEntity(technician.getId(), technician.getName(), technician.getPhone(), technician.getEmail());
        return repository.countByTechnicianAndStatus(technicianEntity, ServiceOrderStatus.IN_PROGRESS);
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByCustomer(String id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum cliente com este id"));
        return repository.findByCustomer(customer).stream().map(so -> mapper.toDomain(so)).toList();
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByEquipment(String id) {
        var equipment = equipmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum equipamento com este id"));
        return repository.findByEquipment(equipment).stream().map(so -> mapper.toDomain(so)).toList();
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByService(String id) {
        var serviceItem = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum serviço com este id"));
        return repository.findByServiceListContaining(serviceItem).stream().map(so -> mapper.toDomain(so)).toList();
    }

    @Override
    public List<ServiceOrder> getServiceOrdersByTechnician(String id) {
        var technician = technicianRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum técnico com este id"));
        return repository.findByTechnician(technician).stream().map(so -> mapper.toDomain(so)).toList();
    }

    private ServiceOrderEntity buildServiceOrderEntity(ServiceOrder serviceOrder){
        var technician = technicianRepository.findById(serviceOrder.getTechnicianId()).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum técnico com este id"));
        var equipment = equipmentRepository.findById(serviceOrder.getEquipmentId()).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum equipamento com este id"));
        var customer = customerRepository.findById(serviceOrder.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum cliente com este id"));
        var serviceList = serviceOrder.getServiceIdList().stream().map(s -> serviceRepository.findById(s).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum serviço com este id"))).toList();
        var price = serviceList.stream()
                .map(ServiceEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        serviceOrder.setPrice(price);

        var serviceOrderEntity = mapper.toEntity(serviceOrder);
        serviceOrderEntity.setTechnician(technician);
        serviceOrderEntity.setEquipment(equipment);
        serviceOrderEntity.setCustomer(customer);
        serviceOrderEntity.setServiceList(serviceList);

        return serviceOrderEntity;
    }
}
