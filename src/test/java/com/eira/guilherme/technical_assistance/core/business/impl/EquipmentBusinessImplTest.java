package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.domain.Equipment;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;
import com.eira.guilherme.technical_assistance.core.service.EquipmentService;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EquipmentBusinessImplTest {

    @InjectMocks
    EquipmentBusinessImpl business;

    @Mock
    EquipmentService service;

    @Mock
    ServiceOrderService serviceOrderService;

    @Test
    void shouldCreateEquipment() {
        var equipment = createEquipment();

        BDDMockito.given(service.serialNumberAlreadyInUse(equipment.getSerialNumber())).willReturn(false);

        BDDMockito.given(service.saveEquipment(equipment)).willReturn(equipment);

        var savedEquipment = business.createEquipment(equipment);

        Assertions.assertEquals(equipment.getId(), savedEquipment.getId());
        Assertions.assertEquals(equipment.getBrand(), savedEquipment.getBrand());
        Assertions.assertEquals(equipment.getModel(), savedEquipment.getModel());
        Assertions.assertEquals(equipment.getType(), savedEquipment.getType());
        Assertions.assertEquals(equipment.getSerialNumber(), savedEquipment.getSerialNumber());
    }

    @Test
    void shouldReturnAllEquipments() {
        var equipment = createEquipment();
        BDDMockito.given(service.getAllEquipments()).willReturn(List.of(equipment));

        var equipmentList = business.getAllEquipments();

        Assertions.assertEquals(1, equipmentList.size());
        Assertions.assertEquals(equipment.getId(), equipmentList.getFirst().getId());
        Assertions.assertEquals(equipment.getSerialNumber(), equipmentList.getFirst().getSerialNumber());
    }

    @Test
    void shouldReturnEquipmentById() {
        var equipment = createEquipment();
        BDDMockito.given(service.getEquipmentById(equipment.getId())).willReturn(equipment);

        var foundEquipment = business.getEquipmentById(equipment.getId());

        Assertions.assertEquals(equipment.getId(), foundEquipment.getId());
        Assertions.assertEquals(equipment.getSerialNumber(), foundEquipment.getSerialNumber());
    }

    @Test
    void shouldUpdateEquipment() {
        var original = createEquipment();
        var update = new Equipment(null, "Apple", "Iphone 16", null, null);

        BDDMockito.given(service.serialNumberAlreadyInUse(update.getSerialNumber())).willReturn(false);
        BDDMockito.given(service.getEquipmentById(original.getId())).willReturn(original);

        var updated = original.update(update);
        BDDMockito.given(service.saveEquipment(updated)).willReturn(updated);

        var savedEquipment = business.updateEquipment(original.getId(), update);

        Assertions.assertEquals(updated.getId(), savedEquipment.getId());
        Assertions.assertEquals(updated.getBrand(), savedEquipment.getBrand());
        Assertions.assertEquals(updated.getModel(), savedEquipment.getModel());
        Assertions.assertEquals(updated.getType(), savedEquipment.getType());
        Assertions.assertEquals(updated.getSerialNumber(), savedEquipment.getSerialNumber());
    }

    @Test
    void shouldDeleteEquipment() {
        var equipment = createEquipment();

        business.deleteEquipment(equipment.getId());

        then(service).should().deleteEquipment(equipment.getId());
    }

    @Test
    void shouldReturnServiceOrdersByEquipment() {
        var equipment = createEquipment();
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withEquipmentId(equipment.getId())
                .build();

        BDDMockito.given(serviceOrderService.getServiceOrdersByEquipment(equipment.getId()))
                .willReturn(List.of(serviceOrder));

        var serviceOrders = business.getServiceOrdersByEquipment(equipment.getId());

        Assertions.assertEquals(1, serviceOrders.size());
        Assertions.assertEquals(serviceOrder.getId(), serviceOrders.getFirst().getId());
    }


        @Test
    void shouldThrowBusinessExceptionIfSerialNumberAlreadyExistsOnCreate() {
        var equipment = createEquipment();
        BDDMockito.given(service.serialNumberAlreadyInUse(equipment.getSerialNumber())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.createEquipment(equipment));
    }

    @Test
    void shouldThrowBusinessExceptionIfSerialNumberAlreadyExistsOnUpdate() {
        var equipment = createEquipment();
        BDDMockito.given(service.serialNumberAlreadyInUse(equipment.getSerialNumber())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.updateEquipment("60188d9c-d4e1-457c-b3c8-df8575611923", equipment));
    }

    private Equipment createEquipment(){
        return new Equipment(
                "60188d9c-d4e1-457c-b3c8-df8575611923",
                "Samsung",
                "Galaxy S23",
                EquipmentType.CELLPHONE,
                "SMG987654321"
        );
    }
}