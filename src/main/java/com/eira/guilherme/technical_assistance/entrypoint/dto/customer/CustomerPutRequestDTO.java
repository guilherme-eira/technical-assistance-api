package com.eira.guilherme.technical_assistance.entrypoint.dto.customer;

import com.eira.guilherme.technical_assistance.core.domain.enums.CustomerType;
import jakarta.validation.constraints.Email;

public record CustomerPutRequestDTO(
        String name,
        String phone,
        @Email(message = "Preencha o campo 'email' com um email válido")
        String email,
        String document,
        CustomerType type
) {
}
