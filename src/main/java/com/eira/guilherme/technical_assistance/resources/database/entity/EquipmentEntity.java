package com.eira.guilherme.technical_assistance.resources.database.entity;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "equipment")
public class EquipmentEntity {
    @Id
    private String id;
    private String brand;
    private String model;
    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    private String serialNumber;
}
