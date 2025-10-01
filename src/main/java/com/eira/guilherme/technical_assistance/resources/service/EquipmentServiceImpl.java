package com.eira.guilherme.technical_assistance.resources.service;

import com.eira.guilherme.technical_assistance.commom.exception.ResourceNotFoundException;
import com.eira.guilherme.technical_assistance.commom.mapper.EquipmentMapper;
import com.eira.guilherme.technical_assistance.core.domain.Equipment;
import com.eira.guilherme.technical_assistance.resources.database.repository.EquipmentRepository;
import com.eira.guilherme.technical_assistance.core.service.EquipmentService;
import com.eira.guilherme.technical_assistance.resources.database.repository.ServiceOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentRepository repository;

    @Autowired
    EquipmentMapper mapper;

    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @Override
    public Equipment saveEquipment(Equipment equipment) {
        return mapper.toDomain(repository.save(mapper.toEntity(equipment)));
    }

    @Override
    public List<Equipment> getAllEquipments() {
        return repository.findAll().stream().map(e -> mapper.toDomain(e)).toList();
    }

    @Override
    public Equipment getEquipmentById(String id) {
        var equipment = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum equipamento com este id"));
        return mapper.toDomain(equipment);
    }

    @Override
    public void deleteEquipment(String id) {
        var equipmentToBeDeleted = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum equipamento com este id"));
        serviceOrderRepository.deleteByEquipment(equipmentToBeDeleted);
        repository.delete(equipmentToBeDeleted);
    }

    @Override
    public Boolean serialNumberAlreadyInUse(String serialNumber) {
        return repository.existsBySerialNumber(serialNumber);
    }
}
