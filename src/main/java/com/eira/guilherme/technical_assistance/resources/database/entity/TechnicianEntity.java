package com.eira.guilherme.technical_assistance.resources.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "technician")
public class TechnicianEntity {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
}
