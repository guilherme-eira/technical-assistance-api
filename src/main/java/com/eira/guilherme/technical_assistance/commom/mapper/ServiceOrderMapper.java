package com.eira.guilherme.technical_assistance.commom.mapper;

import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderResponseDTO;
import com.eira.guilherme.technical_assistance.resources.database.entity.ServiceEntity;
import com.eira.guilherme.technical_assistance.resources.database.entity.ServiceOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceOrderMapper {

    public ServiceOrder toDomain(ServiceOrderPostRequestDTO dto){
        return new ServiceOrder.Builder()
                .withEntryDate(dto.entryDate())
                .withEstimatedDeliveryDate(dto.estimatedDeliveryDate())
                .withDefect(dto.defect())
                .withPriority(dto.priority())
                .withNotes(dto.notes())
                .withTechnicianId(dto.technicianId())
                .withEquipmentId(dto.equipmentId())
                .withCustomerId(dto.customerId())
                .withServiceIdList(dto.serviceIdList())
                .build();
    }

    public ServiceOrder toDomain(ServiceOrderPutRequestDTO dto){
        return new ServiceOrder.Builder()
                .withEstimatedDeliveryDate(dto.estimatedDeliveryDate())
                .withDefect(dto.defect())
                .withPriority(dto.priority())
                .withNotes(dto.notes())
                .withTechnicianId(dto.technicianId())
                .withCustomerId(dto.customerId())
                .build();
    };

    public ServiceOrder toDomain(ServiceOrderEntity entity){
        return new ServiceOrder.Builder()
                .withId(entity.getId())
                .withEntryDate(entity.getEntryDate())
                .withEstimatedDeliveryDate(entity.getEstimatedDeliveryDate())
                .withDefect(entity.getDefect())
                .withExitDate(entity.getExitDate())
                .withStatus(entity.getStatus())
                .withPriority(entity.getPriority())
                .withNotes(entity.getNotes())
                .withPrice(entity.getPrice())
                .withTechnicianId(entity.getTechnician().getId())
                .withEquipmentId(entity.getEquipment().getId())
                .withCustomerId(entity.getCustomer().getId())
                .withServiceIdList(entity.getServiceList().stream().map(ServiceEntity::getId).toList())
                .build();
    }

    public ServiceOrderResponseDTO toDto(ServiceOrder domain){
        return new ServiceOrderResponseDTO(
                domain.getId(),
                domain.getEntryDate(),
                domain.getEstimatedDeliveryDate(),
                domain.getDefect(),
                domain.getExitDate(),
                domain.getStatus(),
                domain.getPriority(),
                domain.getNotes(),
                domain.getPrice(),
                domain.getTechnicianId(),
                domain.getEquipmentId(),
                domain.getCustomerId(),
                domain.getServiceIdList()
                );
    }

    public ServiceOrderEntity toEntity(ServiceOrder domain) {
        return new ServiceOrderEntity(
                domain.getId(),
                domain.getEntryDate(),
                domain.getEstimatedDeliveryDate(),
                domain.getDefect(),
                domain.getExitDate(),
                domain.getStatus(),
                domain.getPriority(),
                domain.getNotes(),
                domain.getPrice()
        );
    }
}
