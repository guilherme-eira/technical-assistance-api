package com.eira.guilherme.technical_assistance.resources.service;

import com.eira.guilherme.technical_assistance.commom.exception.ResourceNotFoundException;
import com.eira.guilherme.technical_assistance.commom.mapper.ServiceMapper;
import com.eira.guilherme.technical_assistance.core.service.ServiceService;
import com.eira.guilherme.technical_assistance.resources.database.repository.ServiceOrderRepository;
import com.eira.guilherme.technical_assistance.resources.database.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.eira.guilherme.technical_assistance.core.domain.Service;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository repository;

    @Autowired
    ServiceMapper mapper;

    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @Override
    public Service saveService(Service service) {
        return mapper.toDomain(repository.save(mapper.toEntity(service)));
    }

    @Override
    public List<Service> getAllServices() {
        return repository.findAll().stream().map(e -> mapper.toDomain(e)).toList();
    }

    @Override
    public Service getServiceById(String id) {
        var service = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum serviço com este id"));
        return mapper.toDomain(service);
    }

    @Override
    public void deleteService(String id) {
        var serviceToBeDeleted = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum serviço com este id"));
        serviceOrderRepository.deleteByServiceList(serviceToBeDeleted);
        repository.delete(serviceToBeDeleted);
    }
}
