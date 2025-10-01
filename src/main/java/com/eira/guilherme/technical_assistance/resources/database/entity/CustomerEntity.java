package com.eira.guilherme.technical_assistance.resources.database.entity;

import com.eira.guilherme.technical_assistance.core.domain.enums.CustomerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
    private String document;
    @Enumerated(EnumType.STRING)
    private CustomerType type;
}
