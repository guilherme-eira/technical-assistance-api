package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.core.domain.Service;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;
import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;
import com.eira.guilherme.technical_assistance.core.service.ServiceOrderService;
import com.eira.guilherme.technical_assistance.core.service.ServiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ServiceBusinessImplTest {

    @InjectMocks
    ServiceBusinessImpl business;

    @Mock
    ServiceService service;

    @Mock
    ServiceOrderService serviceOrderService;

    @Test
    void shouldCreateService() {
        var svc = createService();
        BDDMockito.given(service.saveService(svc)).willReturn(svc);

        var savedService = business.createService(svc);

        Assertions.assertEquals(svc.getId(), savedService.getId());
        Assertions.assertEquals(svc.getDescription(), savedService.getDescription());
        Assertions.assertEquals(svc.getPrice(), savedService.getPrice());
        Assertions.assertEquals(svc.getEquipmentType(), savedService.getEquipmentType());
    }

    @Test
    void shouldReturnAllServices() {
        var svc = createService();
        BDDMockito.given(service.getAllServices()).willReturn(List.of(svc));

        var serviceList = business.getAllServices();

        Assertions.assertEquals(1, serviceList.size());
        Assertions.assertEquals(svc.getId(), serviceList.getFirst().getId());
        Assertions.assertEquals(svc.getDescription(), serviceList.getFirst().getDescription());
    }

    @Test
    void shouldReturnServiceById() {
        var svc = createService();
        BDDMockito.given(service.getServiceById(svc.getId())).willReturn(createService());

        var foundService = business.getServiceById(svc.getId());

        Assertions.assertEquals(svc.getId(), foundService.getId());
        Assertions.assertEquals(svc.getDescription(), foundService.getDescription());
    }

    @Test
    void shouldUpdateService() {
        var original = createService();
        var update = new Service(null, "Limpeza de tela do smartphone", null, null);

        BDDMockito.given(service.getServiceById(original.getId())).willReturn(original);

        var updated = original.update(update);
        BDDMockito.given(service.saveService(updated)).willReturn(updated);

        var savedService = business.updateService(original.getId(), update);

        Assertions.assertEquals(updated.getId(), savedService.getId());
        Assertions.assertEquals(updated.getDescription(), savedService.getDescription());
        Assertions.assertEquals(updated.getPrice(), savedService.getPrice());
        Assertions.assertEquals(updated.getEquipmentType(), savedService.getEquipmentType());
    }

    @Test
    void deleteService() {
        var svc = createService();

        business.deleteService(svc.getId());

        then(service).should().deleteService(svc.getId());
    }

    @Test
    void shouldReturnServiceOrdersByService() {
        var svc = createService();
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withServiceIdList(List.of(svc.getId()))
                .build();
        BDDMockito.given(serviceOrderService.getServiceOrdersByService(svc.getId())).willReturn(List.of(serviceOrder));

        var serviceOrderList = business.getServiceOrdersByService(svc.getId());

        Assertions.assertEquals(1, serviceOrderList.size());
        Assertions.assertEquals(serviceOrder.getId(), serviceOrderList.getFirst().getId());
    }

    private Service createService() {
        return new Service(
                "02ccb2ff-da0e-44ce-9868-b73536d4bfc2",
                "Troca de tela do smartphone",
                BigDecimal.valueOf(250.00),
                EquipmentType.CELLPHONE
        );
    }
}