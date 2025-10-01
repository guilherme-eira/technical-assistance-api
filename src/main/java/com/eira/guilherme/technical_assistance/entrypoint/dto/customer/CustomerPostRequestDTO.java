package com.eira.guilherme.technical_assistance.entrypoint.dto.customer;

import com.eira.guilherme.technical_assistance.core.domain.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerPostRequestDTO(
        @NotBlank(message = "O campo 'name' é obrigatório")
        String name,
        @NotBlank(message = "O campo 'phone' é obrigatório")
        String phone,
        @NotBlank(message = "O campo 'email' é obrigatório")
        @Email(message = "Preencha o campo 'email' com um email válido")
        String email,
        @NotBlank(message = "O campo 'document' é obrigatório")
        String document,
        @NotNull(message = "O campo 'type' é obrigatório")
        CustomerType type
) {
}
