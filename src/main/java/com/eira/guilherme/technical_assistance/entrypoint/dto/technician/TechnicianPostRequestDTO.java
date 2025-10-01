package com.eira.guilherme.technical_assistance.entrypoint.dto.technician;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TechnicianPostRequestDTO(
    @NotBlank(message = "o campo 'name' é obrigatório")
    String name,
    @NotBlank(message = "o campo 'phone' é obrigatório")
    String phone,
    @NotBlank(message = "o campo 'email' é obrigatório")
    @Email(message = "o campo 'email' deve ser preenchido com um email válido")
    String email
) {}
