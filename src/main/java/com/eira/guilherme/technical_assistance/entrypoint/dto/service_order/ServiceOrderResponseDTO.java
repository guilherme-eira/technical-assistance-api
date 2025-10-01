package com.eira.guilherme.technical_assistance.entrypoint.dto.service_order;

import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceOrderResponseDTO(
        String id,
        LocalDate entryDate,
        LocalDate estimatedDeliveryDate,
        String defect,
        LocalDate exitDate,
        ServiceOrderStatus status,
        Integer priority,
        String notes,
        BigDecimal price,
        String technicianId,
        String equipmentId,
        String customerId,
        List<String> serviceIdList
) {
}
