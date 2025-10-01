package com.eira.guilherme.technical_assistance.entrypoint.dto.service;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;

import java.math.BigDecimal;

public record ServiceResponseDTO(
        String id,
        String description,
        EquipmentType equipmentType,
        BigDecimal price

) {
}
