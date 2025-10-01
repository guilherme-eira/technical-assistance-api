package com.eira.guilherme.technical_assistance.entrypoint.dto.service;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServicePostRequestDTO(
    @NotBlank(message = "O campo 'description' é obrigatório")
    String description,
    @NotNull(message = "O campo 'price' é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "O preço deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    BigDecimal price,
    @NotNull(message = "O campo 'equipmentType' é obrigatório")
    EquipmentType equipmentType
) {
}
