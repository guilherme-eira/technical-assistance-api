package com.eira.guilherme.technical_assistance.entrypoint.dto.customer;

import com.eira.guilherme.technical_assistance.core.domain.enums.CustomerType;

public record CustomerResponseDTO(
        String id,
        String name,
        String phone,
        String email,
        String document,
        CustomerType type
){
}
