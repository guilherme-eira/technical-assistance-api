package com.eira.guilherme.technical_assistance.commom.mapper;

import com.eira.guilherme.technical_assistance.core.domain.Technician;
import com.eira.guilherme.technical_assistance.entrypoint.dto.technician.TechnicianPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.technician.TechnicianPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.technician.TechnicianResponseDTO;
import com.eira.guilherme.technical_assistance.resources.database.entity.TechnicianEntity;
import org.springframework.stereotype.Component;

@Component
public class TechnicianMapper {

    public Technician toDomain(TechnicianPostRequestDTO dto){
        return new Technician(null, dto.name(), dto.phone(), dto.email());
    }

    public Technician toDomain(TechnicianPutRequestDTO dto){
        return new Technician(null, dto.name(), dto.phone(), dto.email());
    }

    public Technician toDomain(TechnicianEntity entity){
        return new Technician(entity.getId(), entity.getName(), entity.getPhone(), entity.getEmail());
    }

    public TechnicianEntity toEntity(Technician domain) {
        return new TechnicianEntity(domain.getId(), domain.getName(), domain.getPhone(), domain.getEmail());
    }

    public TechnicianResponseDTO toDto(Technician domain) {
        return new TechnicianResponseDTO(domain.getId(), domain.getName(), domain.getPhone(), domain.getEmail());
    }
}
