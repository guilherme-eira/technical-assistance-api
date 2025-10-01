package com.eira.guilherme.technical_assistance.core.domain;

import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;

import java.util.UUID;

public class Equipment {
    private String id;
    private String brand;
    private String model;
    private EquipmentType type;
    private String serialNumber;

    public Equipment(String id, String brand, String model, EquipmentType type, String serialNumber) {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.serialNumber = serialNumber;
    }

    public Equipment update(Equipment equipment) {
        if (equipment.getBrand() != null) {
            this.brand = equipment.getBrand();
        }
        if (equipment.getModel() != null) {
            this.model = equipment.getModel();
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public EquipmentType getType() {
        return type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
