package com.eira.guilherme.technical_assistance.resources.service;

import com.eira.guilherme.technical_assistance.commom.exception.ResourceNotFoundException;
import com.eira.guilherme.technical_assistance.commom.mapper.TechnicianMapper;
import com.eira.guilherme.technical_assistance.core.domain.Technician;
import com.eira.guilherme.technical_assistance.resources.database.repository.ServiceOrderRepository;
import com.eira.guilherme.technical_assistance.resources.database.repository.TechnicianRepository;
import com.eira.guilherme.technical_assistance.core.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService {

    @Autowired
    TechnicianRepository repository;

    @Autowired
    TechnicianMapper mapper;

    @Autowired
    ServiceOrderRepository serviceOrderRepository;

    @Override
    public Technician saveTechnician(Technician technician) {
        return mapper.toDomain(repository.save(mapper.toEntity(technician)));
    }

    @Override
    public List<Technician> getAllTechnicians() {
        return repository.findAll().stream().map(t -> mapper.toDomain(t)).toList();
    }

    @Override
    public Technician getTechnicianById(String id) {
        var technician = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum técnico com este id"));
        return mapper.toDomain(technician);
    }

    @Override
    public void deleteTechnician(String id) {
        var technicianToBeDeleted = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não há nenhum técnico com este id"));
        serviceOrderRepository.deleteByTechnician(technicianToBeDeleted);
        repository.delete(technicianToBeDeleted);
    }

    @Override
    public Boolean nameAlreadyInUse(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Boolean phoneAlreadyInUse(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public Boolean emailAlreadyInUse(String email) {
        return repository.existsByEmail(email);
    }


}
