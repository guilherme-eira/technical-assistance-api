package com.eira.guilherme.technical_assistance.commom.mapper;

import com.eira.guilherme.technical_assistance.core.domain.Equipment;
import com.eira.guilherme.technical_assistance.entrypoint.dto.equipment.EquipmentPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.equipment.EquipmentPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.equipment.EquipmentResponseDTO;
import com.eira.guilherme.technical_assistance.resources.database.entity.EquipmentEntity;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public Equipment toDomain(EquipmentPostRequestDTO dto){
        return new Equipment(null, dto.brand(), dto.model(), dto.type(), dto.serialNumber());
    }

    public Equipment toDomain(EquipmentPutRequestDTO dto){
        return new Equipment(null, dto.brand(), dto.model(), null, null);
    }

    public Equipment toDomain(EquipmentEntity entity){
        return new Equipment(entity.getId(), entity.getBrand(), entity.getModel(), entity.getType(), entity.getSerialNumber());
    }

    public EquipmentEntity toEntity(Equipment domain) {
        return new EquipmentEntity(domain.getId(), domain.getBrand(), domain.getModel(), domain.getType(), domain.getSerialNumber());
    }

    public EquipmentResponseDTO toDto(Equipment domain) {
        return new EquipmentResponseDTO(domain.getId(), domain.getBrand(), domain.getModel(), domain.getType(), domain.getSerialNumber());
    }
}
