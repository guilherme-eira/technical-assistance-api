package com.eira.guilherme.technical_assistance.resources.database.entity;

import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "service_order")
public class ServiceOrderEntity {
    @Id
    private String id;
    private LocalDate entryDate;
    private LocalDate estimatedDeliveryDate;
    private String defect;
    private LocalDate exitDate;
    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus status;
    private Integer priority;
    private String notes;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    private TechnicianEntity technician;
    @ManyToOne(fetch = FetchType.LAZY)
    private EquipmentEntity equipment;
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity customer;
    @ManyToMany
    @JoinTable(
            name = "service_order_service",
            joinColumns = @JoinColumn(name = "service_order_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<ServiceEntity> serviceList;

    public ServiceOrderEntity(String id, LocalDate entryDate, LocalDate estimatedDeliveryDate, String defect, LocalDate exitDate, ServiceOrderStatus status, Integer priority, String notes, BigDecimal price) {
        this.id = id;
        this.entryDate = entryDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.defect = defect;
        this.exitDate = exitDate;
        this.status = status;
        this.priority = priority;
        this.notes = notes;
        this.price = price;
    }
}
