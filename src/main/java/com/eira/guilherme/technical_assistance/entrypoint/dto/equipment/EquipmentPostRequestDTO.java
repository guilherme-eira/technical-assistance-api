package com.eira.guilherme.technical_assistance.entrypoint.dto.equipment;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipmentPostRequestDTO(
        @NotBlank(message = "O campo 'brand' é obrigatório")
        String brand,
        @NotBlank(message = "O campo 'model' é obrigatório")
        String model,
        @NotNull(message = "O campo 'type' é obrigatório")
        EquipmentType type,
        @NotBlank(message = "O campo 'serialNumber' é obrigatório")
        String serialNumber
) {
}
