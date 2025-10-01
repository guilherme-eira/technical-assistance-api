package com.eira.guilherme.technical_assistance.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        String error,
        String message,
        String path,
        Integer status,
        String timestamp,
        Map<String, String> invalidFields
) {
}
