package com.eira.guilherme.technical_assistance.core.domain;

import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ServiceOrder {
    private String id;
    private LocalDate entryDate;
    private LocalDate estimatedDeliveryDate;
    private String defect;
    private LocalDate exitDate;
    private ServiceOrderStatus status;
    private Integer priority;
    private String notes;
    private BigDecimal price;
    private String technicianId;
    private String equipmentId;
    private String customerId;
    private List<String> serviceIdList;

    public ServiceOrder() {
    }

    public static class Builder{
        private final ServiceOrder serviceOrder;

        public Builder() {
            this.serviceOrder = new ServiceOrder();
        }

        public Builder withId(String id){
            this.serviceOrder.setId(id);
            return this;
        }

        public Builder withEntryDate(LocalDate entryDate){
            this.serviceOrder.setEntryDate(entryDate);
            return this;
        }

        public Builder withEstimatedDeliveryDate(LocalDate estimatedDeliveryDate){
            this.serviceOrder.setEstimatedDeliveryDate(estimatedDeliveryDate);
            return this;
        }

        public Builder withDefect(String defect){
            this.serviceOrder.setDefect(defect);
            return this;
        }

        public Builder withExitDate(LocalDate exitDate){
            this.serviceOrder.setExitDate(exitDate);
            return this;
        }

        public Builder withStatus(ServiceOrderStatus status){
            this.serviceOrder.setStatus(status);
            return this;
        }

        public Builder withPriority(Integer priority){
            this.serviceOrder.setPriority(priority);
            return this;
        }

        public Builder withNotes(String notes){
            this.serviceOrder.setNotes(notes);
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.serviceOrder.setPrice(price);
            return this;
        }

        public Builder withTechnicianId(String technicianId){
            this.serviceOrder.setTechnicianId(technicianId);
            return this;
        }

        public Builder withEquipmentId(String equipmentId){
            this.serviceOrder.setEquipmentId(equipmentId);
            return this;
        }

        public Builder withCustomerId(String customerId){
            this.serviceOrder.setCustomerId(customerId);
            return this;
        }

        public Builder withServiceIdList(List<String> serviceIdList){
            this.serviceOrder.setServiceIdList(serviceIdList);
            return this;
        }

        public ServiceOrder build(){
            if (this.serviceOrder.getId() == null){
                this.serviceOrder.setId(UUID.randomUUID().toString());
            } if (this.serviceOrder.getEntryDate() == null){
                this.serviceOrder.setEntryDate(LocalDate.now());
            } if (this.serviceOrder.getStatus() == null){
                this.serviceOrder.setStatus(ServiceOrderStatus.OPEN);
            }
            return this.serviceOrder;
        }
    }

    public ServiceOrder update(ServiceOrder serviceOrder){
        if (serviceOrder.getEstimatedDeliveryDate() != null){
            this.estimatedDeliveryDate = serviceOrder.getEstimatedDeliveryDate();
        }if (serviceOrder.getDefect() != null){
            this.defect = serviceOrder.getDefect();
        }if (serviceOrder.getPriority() != null){
            this.priority = serviceOrder.getPriority();
        }if (serviceOrder.getNotes() != null){
            this.notes = serviceOrder.getNotes();
        }if (serviceOrder.getTechnicianId() != null){
            this.technicianId = serviceOrder.getTechnicianId();
        }if (serviceOrder.getCustomerId() != null) {
            this.customerId = serviceOrder.getCustomerId();
        }
        return this;
    }

    public void start(){
        this.status = ServiceOrderStatus.IN_PROGRESS;
    }

    public void cancel(){
        this.status = ServiceOrderStatus.CANCELED;
    }

    public void complete(){
        this.status = ServiceOrderStatus.COMPLETED;
        this.exitDate = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceOrderStatus status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technician) {
        this.technicianId = technician;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<String> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<String> serviceIdList) {
        this.serviceIdList = serviceIdList;
    }
}
