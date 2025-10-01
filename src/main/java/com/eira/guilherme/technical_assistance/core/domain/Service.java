package com.eira.guilherme.technical_assistance.core.domain;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;

import java.math.BigDecimal;
import java.util.UUID;

public class Service {
    private String id;
    private String description;
    private BigDecimal price;
    private EquipmentType equipmentType;

    public Service(String id, String description, BigDecimal price, EquipmentType equipmentType) {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.description = description;
        this.price = price;
        this.equipmentType = equipmentType;
    }

    public Service update(Service service) {
        if (service.getDescription() != null){
            this.description = service.getDescription();
        } if (service.getPrice() != null){
            this.price = service.getPrice();
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }
}
