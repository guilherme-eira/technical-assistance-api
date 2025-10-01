package com.eira.guilherme.technical_assistance.core.domain;

import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;

import java.time.LocalDate;

public class ServiceOrderTableVO {
    private String id;
    private String customerName;
    private LocalDate entryDate;
    private String defect;
    private LocalDate exitDate;
    private ServiceOrderStatus status;

    public ServiceOrderTableVO(String id, String customerName, LocalDate entryDate, String defect, LocalDate exitDate, ServiceOrderStatus status) {
        this.id = id;
        this.customerName = customerName;
        this.entryDate = entryDate;
        this.defect = defect;
        this.exitDate = exitDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public String getDefect() {
        return defect;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }
}
