package com.eira.guilherme.technical_assistance.core.service;

import com.eira.guilherme.technical_assistance.core.domain.Technician;

import java.util.List;

public interface TechnicianService {
    Technician saveTechnician(Technician technician);
    List<Technician> getAllTechnicians();
    Technician getTechnicianById(String id);
    void deleteTechnician(String id);
    Boolean nameAlreadyInUse(String name);
    Boolean phoneAlreadyInUse(String phone);
    Boolean emailAlreadyInUse(String email);
}
