package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.Technician;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import com.eira.guilherme.technical_assistance.resources.service.TechnicianServiceImpl;
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
class TechnicianBusinessImplTest {

    @InjectMocks
    TechnicianBusinessImpl business;

    @Mock
    TechnicianServiceImpl service;

    @Mock
    ServiceOrderService serviceOrderService;

    private final String ID = "39151ef3-2348-4f0d-95a8-b4f793780322";

    @Test
    void shouldCreateTechnician() {
        var technician = createTechnician();

        BDDMockito.given(service.nameAlreadyInUse(technician.getName())).willReturn(false);
        BDDMockito.given(service.phoneAlreadyInUse(technician.getPhone())).willReturn(false);
        BDDMockito.given(service.emailAlreadyInUse(technician.getEmail())).willReturn(false);

        BDDMockito.given(service.saveTechnician(technician)).willReturn(technician);

        var savedTechnician = business.createTechnician(technician);

        Assertions.assertEquals(technician.getId(), savedTechnician.getId());
        Assertions.assertEquals(technician.getName(), savedTechnician.getName());
        Assertions.assertEquals(technician.getPhone(), savedTechnician.getPhone());
        Assertions.assertEquals(technician.getEmail(), savedTechnician.getEmail());
    }

    @Test
    void shouldReturnAllTechnicians() {
        var technician = createTechnician();
        BDDMockito.given(service.getAllTechnicians()).willReturn(List.of(technician));

        var technicianList = business.getAllTechnicians();

        Assertions.assertEquals(1, technicianList.size());
        Assertions.assertEquals(technician.getId(), technicianList.getFirst().getId());
        Assertions.assertEquals(technician.getName(), technicianList.getFirst().getName());
    }

    @Test
    void shouldReturnTechnicianById() {
        var technician = createTechnician();
        BDDMockito.given(service.getTechnicianById(technician.getId())).willReturn(technician);

        var foundTechnician = business.getTechnicianById(technician.getId());

        Assertions.assertEquals(technician.getId(), foundTechnician.getId());
        Assertions.assertEquals(technician.getName(), foundTechnician.getName());
    }

    @Test
    void shouldUpdateTechnician() {
        var original = createTechnician();
        var update = new Technician(null, "Lucas", "11976543210", "lucas@email.com");

        BDDMockito.given(service.nameAlreadyInUse(update.getName())).willReturn(false);
        BDDMockito.given(service.phoneAlreadyInUse(update.getPhone())).willReturn(false);
        BDDMockito.given(service.emailAlreadyInUse(update.getEmail())).willReturn(false);

        BDDMockito.given(service.getTechnicianById(original.getId())).willReturn(original);

        var updated = original.update(update);
        BDDMockito.given(service.saveTechnician(updated)).willReturn(updated);

        var savedTechnician = business.updateTechnician(original.getId(), update);

        Assertions.assertEquals(updated.getId(), savedTechnician.getId());
        Assertions.assertEquals(updated.getName(), savedTechnician.getName());
        Assertions.assertEquals(updated.getPhone(), savedTechnician.getPhone());
        Assertions.assertEquals(updated.getEmail(), savedTechnician.getEmail());
    }

    @Test
    void shouldDeleteTechnician() {
        var technician = createTechnician();

        business.deleteTechnician(technician.getId());

        then(service).should().deleteTechnician(technician.getId());
    }

    @Test
    void shouldReturnServiceOrdersByTechnician() {
        var technician = createTechnician();
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withTechnicianId(technician.getId())
                .build();

        BDDMockito.given(serviceOrderService.getServiceOrdersByTechnician(technician.getId()))
                .willReturn(List.of(serviceOrder));

        var serviceOrders = business.getServiceOrdersByTechnician(technician.getId());

        Assertions.assertEquals(1, serviceOrders.size());
        Assertions.assertEquals(serviceOrder.getId(), serviceOrders.getFirst().getId());
    }

    @Test
    void shouldThrowBusinessExceptionIfNameAlreadyExistsOnCreate() {
        var technician = createTechnician();
        BDDMockito.given(service.nameAlreadyInUse(technician.getName())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> {
            business.createTechnician(technician);
        });
    }

    @Test
    void shouldThrowBusinessExceptionIfPhoneAlreadyExistsOnCreate() {
        var technician = createTechnician();
        BDDMockito.given(service.phoneAlreadyInUse(technician.getPhone())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> {
            business.createTechnician(technician);
        });
    }

    @Test
    void shouldThrowBusinessExceptionIfEmailAlreadyExistsOnCreate() {
        var technician = createTechnician();
        BDDMockito.given(service.emailAlreadyInUse(technician.getEmail())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> {
            business.createTechnician(technician);
        });
    }

    @Test
    void shouldThrowBusinessExceptionIfNameAlreadyExistsOnUpdate() {
        var technician = createTechnician();
        BDDMockito.given(service.nameAlreadyInUse(technician.getName())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> {
            business.updateTechnician(ID, technician);
        });
    }

    @Test
    void shouldThrowBusinessExceptionIfPhoneAlreadyExistsOnUpdate() {
        var technician = createTechnician();
        BDDMockito.given(service.phoneAlreadyInUse(technician.getPhone())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> {
            business.updateTechnician(ID, technician);
        });
    }

    @Test
    void shouldThrowBusinessExceptionIfEmailAlreadyExistsOnUpdate() {
        var technician = createTechnician();
        BDDMockito.given(service.emailAlreadyInUse(technician.getEmail())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> {
            business.updateTechnician(ID, technician);
        });
    }

    private Technician createTechnician() {
        return new Technician(ID,
                "Guilherme",
                "123456789",
                "guilherme@email.com");
    }

}