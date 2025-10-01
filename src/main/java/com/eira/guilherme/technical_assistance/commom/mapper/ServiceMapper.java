package com.eira.guilherme.technical_assistance.commom.mapper;

import com.eira.guilherme.technical_assistance.core.domain.Service;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service.ServicePostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service.ServicePutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service.ServiceResponseDTO;
import com.eira.guilherme.technical_assistance.resources.database.entity.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    public Service toDomain(ServicePostRequestDTO dto){
        return new Service(null, dto.description(), dto.price(), dto.equipmentType());
    }

    public Service toDomain(ServicePutRequestDTO dto){
        return new Service(null, dto.description(), dto.price(), null);
    }

    public Service toDomain(ServiceEntity entity){
        return new Service(entity.getId(), entity.getDescription(), entity.getPrice(), entity.getEquipmentType());
    }

    public ServiceEntity toEntity(Service domain) {
        return new ServiceEntity(domain.getId(), domain.getDescription(), domain.getPrice(), domain.getEquipmentType());
    }

    public ServiceResponseDTO toDto(Service domain) {
        return new ServiceResponseDTO(domain.getId(), domain.getDescription(), domain.getEquipmentType(), domain.getPrice());
    }
}
