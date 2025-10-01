package com.eira.guilherme.technical_assistance.core.domain;

import java.util.UUID;

public class Technician {
    private String id;
    private String name;
    private String phone;
    private String email;

    public Technician(String id, String name, String phone, String email) {
        if (id == null){
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Technician update(Technician technician) {
        if (technician.getName() != null){
            this.name = technician.getName();
        }if (technician.getPhone() != null){
            this.phone = technician.getPhone();
        }if (technician.getEmail() != null){
            this.email = technician.getEmail();
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
