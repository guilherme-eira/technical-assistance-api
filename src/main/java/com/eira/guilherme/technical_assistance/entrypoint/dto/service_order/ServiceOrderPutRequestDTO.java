package com.eira.guilherme.technical_assistance.entrypoint.dto.service_order;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ServiceOrderPutRequestDTO(
        @FutureOrPresent(message = "A data estimada de entrega deve ser atual ou futura")
        LocalDate estimatedDeliveryDate,
        String defect,
        @Min(value = 1, message = "A prioridade deve ser no mínimo 1")
        @Max(value = 5, message = "A prioridade deve ser no máximo 5")
        Integer priority,
        String notes,
        String technicianId,
        String customerId
) {}
