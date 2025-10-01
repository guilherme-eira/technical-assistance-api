package com.eira.guilherme.technical_assistance.resources.database.repository;

import com.eira.guilherme.technical_assistance.core.domain.ServiceOrderTableVO;
import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;
import com.eira.guilherme.technical_assistance.resources.database.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrderEntity, String> {
    Integer countByTechnicianAndStatus(TechnicianEntity technician, ServiceOrderStatus status);
    @Query("SELECT new com.eira.guilherme.technical_assistance.core.domain.ServiceOrderTableVO(so.id, c.name, so.entryDate, so.defect, so.exitDate, so.status) " +
            "FROM ServiceOrderEntity so JOIN so.customer c")
    List<ServiceOrderTableVO> findAllServiceOrdersForTable();
    @Query("SELECT new com.eira.guilherme.technical_assistance.core.domain.ServiceOrderTableVO(so.id, c.name, so.entryDate, so.defect, so.exitDate, so.status) " +
            "FROM ServiceOrderEntity so JOIN so.customer c WHERE c.name = :name")
    List<ServiceOrderTableVO> findAllServiceOrdersForTableByCustomerName(@Param("name") String name);
    List<ServiceOrderEntity> findByCustomer(CustomerEntity customer);
    List<ServiceOrderEntity> findByEquipment(EquipmentEntity equipment);
    List<ServiceOrderEntity> findByServiceListContaining(ServiceEntity service);
    List<ServiceOrderEntity> findByTechnician(TechnicianEntity technician);
    void deleteByCustomer(CustomerEntity customer);
    void deleteByEquipment(EquipmentEntity equipment);
    void deleteByServiceList(ServiceEntity service);
    void deleteByTechnician(TechnicianEntity technician);
}
