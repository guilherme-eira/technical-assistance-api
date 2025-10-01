package com.eira.guilherme.technical_assistance.entrypoint.dto.service_order;

import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;

import java.time.LocalDate;

public record ServiceOrderTableDTO(
        String id,
        String customerName,
        LocalDate entryDate,
        String defect,
        LocalDate exitDate,
        ServiceOrderStatus status
) {
}
