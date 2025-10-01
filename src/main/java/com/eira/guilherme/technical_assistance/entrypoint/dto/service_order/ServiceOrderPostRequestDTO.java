package com.eira.guilherme.technical_assistance.entrypoint.dto.service_order;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ServiceOrderPostRequestDTO(
        LocalDate entryDate,
        @FutureOrPresent(message = "A data estimada de entrega deve ser atual ou futura")
        @NotNull
        LocalDate estimatedDeliveryDate,
        @NotBlank(message = "O campo 'defect' é obrigatório")
        String defect,
        @NotNull(message = "O campo 'priority' é obrigatório")
        @Min(value = 1, message = "A prioridade deve ser no mínimo 1")
        @Max(value = 5, message = "A prioridade deve ser no máximo 5")
        Integer priority,
        String notes,
        @NotBlank(message = "O campo 'technicianId' é obrigatório")
        String technicianId,
        @NotBlank(message = "O campo 'equipmentId' é obrigatório")
        String equipmentId,
        @NotBlank(message = "O campo 'customerId' é obrigatório")
        String customerId,
        @NotEmpty(message = "O campo 'serviceIdList' é obrigatório")
        @Size(min = 1, max = 4, message = "A lista de serviços deve conter no mínimo 1 serviço e no máximo 4")
        List<String> serviceIdList
) {
}
