package com.eira.guilherme.technical_assistance.entrypoint.dto.technician;

import jakarta.validation.constraints.Email;

public record TechnicianPutRequestDTO(
        String name,
        String phone,
        @Email(message = "o campo 'email' deve ser preenchido com um email válido")
        String email
) {
}
