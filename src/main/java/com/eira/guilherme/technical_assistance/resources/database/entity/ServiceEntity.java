package com.eira.guilherme.technical_assistance.resources.database.entity;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    private String id;
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;
}
