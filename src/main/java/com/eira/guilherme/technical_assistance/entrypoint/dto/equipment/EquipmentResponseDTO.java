package com.eira.guilherme.technical_assistance.entrypoint.dto.equipment;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;

public record EquipmentResponseDTO(
        String id,
        String brand,
        String model,
        EquipmentType type,
        String serialNumber
) {
}
