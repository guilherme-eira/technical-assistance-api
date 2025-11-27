package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.domain.*;
import com.eira.guilherme.technical_assistance.core.domain.enums.CustomerType;
import com.eira.guilherme.technical_assistance.core.domain.enums.EquipmentType;
import com.eira.guilherme.technical_assistance.core.domain.enums.ServiceOrderStatus;
import com.eira.guilherme.technical_assistance.core.service.*;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderTableDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ServiceOrderBusinessImplTest {

    @InjectMocks
    ServiceOrderBusinessImpl business;

    @Mock
    ServiceOrderService service;

    @Mock
    EquipmentService equipmentService;

    @Mock
    ServiceService serviceService;

    @Mock
    CustomerService customerService;

    @Mock
    TechnicianService technicianService;

    @Captor
    ArgumentCaptor<ServiceOrder> serviceOrderArgumentCaptor;

    @Test
    void shouldCreateServiceOrder() {
        var serviceOrder = createServiceOrder();
        var equipment = createEquipment();
        var svc = createService();

        BDDMockito.given(equipmentService.getEquipmentById(serviceOrder.getEquipmentId())).willReturn(equipment);
        BDDMockito.given(serviceService.getServiceById(serviceOrder.getServiceIdList().getFirst())).willReturn(svc);
        BDDMockito.given(service.saveServiceOrder(serviceOrder)).willReturn(serviceOrder);

        var savedServiceOrder = business.createServiceOrder(serviceOrder);

        Assertions.assertEquals(serviceOrder.getId(), savedServiceOrder.getId());
        Assertions.assertEquals(serviceOrder.getDefect(), savedServiceOrder.getDefect());
        Assertions.assertEquals(serviceOrder.getTechnicianId(), savedServiceOrder.getTechnicianId());
        Assertions.assertEquals(serviceOrder.getEquipmentId(), savedServiceOrder.getEquipmentId());
        Assertions.assertEquals(serviceOrder.getCustomerId(), savedServiceOrder.getCustomerId());
        Assertions.assertEquals(serviceOrder.getServiceIdList(), savedServiceOrder.getServiceIdList());
    }

    @Test
    void shouldReturnAllServiceOrders() {
        var serviceOrder = createServiceOrder();

        BDDMockito.given(service.getAllServiceOrders()).willReturn(List.of(serviceOrder));

        var serviceOrderList = business.getAllServiceOrders();

        Assertions.assertEquals(1, serviceOrderList.size());
        Assertions.assertEquals(serviceOrder.getId(), serviceOrderList.getFirst().getId());
        Assertions.assertEquals(serviceOrder.getDefect(), serviceOrderList.getFirst().getDefect());
    }

    @Test
    void shouldReturnServiceOrderById() {
        var serviceOrder = createServiceOrder();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);

        var foundServiceOrder = business.getServiceOrderById(serviceOrder.getId());

        Assertions.assertEquals(serviceOrder.getId(), foundServiceOrder.getId());
        Assertions.assertEquals(serviceOrder.getDefect(), foundServiceOrder.getDefect());
    }

    @Test
    void shouldReturnServiceOrderTableVo() {
        var serviceOrderTableDto = createServiceOrderTableDto();

        BDDMockito.given(service.getServiceOrdersForTable()).willReturn(List.of(serviceOrderTableDto));

        var serviceOrderTableDtoList = business.getServiceOrdersForTable();

        Assertions.assertEquals(1, serviceOrderTableDtoList.size());
        Assertions.assertEquals(serviceOrderTableDto, serviceOrderTableDtoList.getFirst());
        Assertions.assertEquals(serviceOrderTableDto.customerName(), serviceOrderTableDtoList.getFirst().customerName());
    }

    @Test
    void shouldReturnServiceOrderTableVoByCustomerName() {
        var serviceOrderTableDto = createServiceOrderTableDto();

        BDDMockito.given(service.getServiceOrdersForTableByCustomerName(serviceOrderTableDto.customerName())).willReturn(List.of(serviceOrderTableDto));

        var serviceOrderTableDtoList = business.getServiceOrdersForTableByCustomerName(serviceOrderTableDto.customerName());

        Assertions.assertEquals(1, serviceOrderTableDtoList.size());
        Assertions.assertEquals(serviceOrderTableDto.id(), serviceOrderTableDtoList.getFirst().id());
        Assertions.assertEquals(serviceOrderTableDto.customerName(), serviceOrderTableDtoList.getFirst().customerName());
    }

    @Test
    void shouldUpdateServiceOrder() {
        var original = createServiceOrder();
        var update = new ServiceOrder.Builder().withDefect("Touchscreen falhando").build();

        BDDMockito.given(service.getServiceOrderById(original.getId())).willReturn(original);

        var updated = original.update(update);
        BDDMockito.given(service.saveServiceOrder(updated)).willReturn(updated);

        var savedEquipment = business.updateServiceOrder(original.getId(), update);

        Assertions.assertEquals(original.getId(), savedEquipment.getId());
        Assertions.assertEquals(original.getDefect(), savedEquipment.getDefect());
        Assertions.assertEquals(original.getTechnicianId(), savedEquipment.getTechnicianId());
        Assertions.assertEquals(original.getEquipmentId(), savedEquipment.getEquipmentId());
        Assertions.assertEquals(original.getCustomerId(), savedEquipment.getCustomerId());
        Assertions.assertEquals(original.getServiceIdList(), savedEquipment.getServiceIdList());
    }

    @Test
    void shouldDeleteServiceOrder() {
        var serviceOrder = createServiceOrder();

        business.deleteServiceOrder(serviceOrder.getId());

        then(service).should().deleteServiceOrder(serviceOrder.getId());
    }

    @Test
    void shouldStartServiceOrder() {
        var technician = createTechnician();
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withStatus(ServiceOrderStatus.OPEN)
                .withTechnicianId(technician.getId())
                .build();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);
        BDDMockito.given(technicianService.getTechnicianById(technician.getId())).willReturn(technician);
        BDDMockito.given(service.countServiceOrdersByTechnician(technician)).willReturn(2);
        BDDMockito.given(service.saveServiceOrder(serviceOrder)).willReturn(serviceOrder);
        business.start(serviceOrder.getId());

        then(service).should().saveServiceOrder(serviceOrderArgumentCaptor.capture());
        var savedServiceOrder = serviceOrderArgumentCaptor.getValue();
        Assertions.assertEquals(ServiceOrderStatus.IN_PROGRESS, savedServiceOrder.getStatus());
    }

    @Test
    void shouldCancelServiceOrder() {
        var serviceOrder = createServiceOrder();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);
        business.cancel(serviceOrder.getId());

        then(service).should().saveServiceOrder(serviceOrderArgumentCaptor.capture());
        var savedServiceOrder = serviceOrderArgumentCaptor.getValue();
        Assertions.assertEquals(ServiceOrderStatus.CANCELED, savedServiceOrder.getStatus());
    }

    @Test
    void shouldCompleteServiceOrder() {
        var serviceOrder = createServiceOrder();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);
        business.complete(serviceOrder.getId());

        then(service).should().saveServiceOrder(serviceOrderArgumentCaptor.capture());
        var savedServiceOrder = serviceOrderArgumentCaptor.getValue();
        Assertions.assertEquals(ServiceOrderStatus.COMPLETED, savedServiceOrder.getStatus());
    }

    @Test
    void shouldThrowBusinessExceptionWhenServiceAndEquipmentAreNotCompatible(){
        var serviceOrder = createServiceOrder();
        var equipment = new Equipment(
                "60188d9c-d4e1-457c-b3c8-df8575611923",
                "Dell",
                "Inspiron 15",
                EquipmentType.LAPTOP,
                "SMG987654321"
        );
        var svc = createService();

        BDDMockito.given(equipmentService.getEquipmentById(serviceOrder.getEquipmentId())).willReturn(equipment);
        BDDMockito.given(serviceService.getServiceById(serviceOrder.getServiceIdList().getFirst())).willReturn(svc);

        Assertions.assertThrows(BusinessException.class, () -> business.createServiceOrder(serviceOrder));
    }

    @Test
    void shouldThrowBusinessExceptionWhenServiceOrderToBeUpdatedIsAlreadyFinished(){
        var original = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withStatus(ServiceOrderStatus.COMPLETED)
                .build();
        var update = new ServiceOrder.Builder().withDefect("Touchscreen falhando").build();

        BDDMockito.given(service.getServiceOrderById(original.getId())).willReturn(original);

        Assertions.assertThrows(BusinessException.class, () -> business.updateServiceOrder(original.getId(), update));
    }

    @Test
    void shouldThrowBusinessExceptionWhenTechnicianExceedsOngoingServiceLimit(){
        var technician = createTechnician();
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withStatus(ServiceOrderStatus.OPEN)
                .withTechnicianId(technician.getId())
                .build();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);
        BDDMockito.given(technicianService.getTechnicianById(technician.getId())).willReturn(technician);
        BDDMockito.given(service.countServiceOrdersByTechnician(technician)).willReturn(4);

        Assertions.assertThrows(BusinessException.class, () -> business.start(serviceOrder.getId()));
    }

    @Test
    void shouldThrowBusinessExceptionWhenServiceOrderToBeStartedIsNotOpen(){
        var technician = createTechnician();
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withStatus(ServiceOrderStatus.COMPLETED)
                .withTechnicianId(technician.getId())
                .build();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);
        BDDMockito.given(technicianService.getTechnicianById(technician.getId())).willReturn(technician);
        BDDMockito.given(service.countServiceOrdersByTechnician(technician)).willReturn(2);

        Assertions.assertThrows(
                BusinessException.class,
                () -> business.start(serviceOrder.getId())
        );
    }

    @Test
    void shouldThrowBusinessExceptionWhenServiceOrderToBeCanceledIsAlreadyFinished() {
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withStatus(ServiceOrderStatus.COMPLETED)
                .build();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);

        Assertions.assertThrows(BusinessException.class, () -> business.cancel(serviceOrder.getId()));
    }

    @Test
    void shouldThrowBusinessExceptionWhenServiceOrderToBeCompletedIsNotInProgress() {
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withStatus(ServiceOrderStatus.COMPLETED)
                .build();

        BDDMockito.given(service.getServiceOrderById(serviceOrder.getId())).willReturn(serviceOrder);

        Assertions.assertThrows(BusinessException.class, () -> business.complete(serviceOrder.getId()));
    }

    @Test
    void shouldThrowBusinessExceptionWhenEntryIsAfterEstimatedDateOnCreate(){
        var serviceOrder = new ServiceOrder.Builder()
                .withEntryDate(LocalDate.parse("2025-09-16"))
                .withEstimatedDeliveryDate(LocalDate.parse("2025-09-15"))
                .build();

        Assertions.assertThrows(BusinessException.class, () -> business.createServiceOrder(serviceOrder));
    }

    @Test
    void shouldThrowBusinessExceptionWhenEntryIsAfterEstimatedDateOnUpdate(){
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withEntryDate(LocalDate.parse("2025-09-16"))
                .build();

        var update = new ServiceOrder.Builder()
                .withEstimatedDeliveryDate(LocalDate.parse("2025-09-15"))
                        .build();

        BDDMockito.given(service.getServiceOrderById("5f7afbd9-cfe9-4859-8c45-4cf521547108")).willReturn(serviceOrder);

        Assertions.assertThrows(BusinessException.class, () -> business.updateServiceOrder("5f7afbd9-cfe9-4859-8c45-4cf521547108", update));
    }

    private ServiceOrder createServiceOrder(){
        return new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withEntryDate(LocalDate.now())
                .withEstimatedDeliveryDate(LocalDate.now().plusDays(3))
                .withDefect("Tela trincada")
                .withStatus(ServiceOrderStatus.IN_PROGRESS)
                .withPriority(3)
                .withPrice(BigDecimal.valueOf(250.00))
                .withTechnicianId("7bfcf17b-c9ff-49b0-848e-36c482e06bb7")
                .withEquipmentId("60188d9c-d4e1-457c-b3c8-df8575611923")
                .withCustomerId("2bd3147b-e1bb-4a3f-973e-7d4b08e70c0c")
                .withServiceIdList(List.of("02ccb2ff-da0e-44ce-9868-b73536d4bfc2"))
                .build();
    }

    private Service createService() {
        return new Service(
                "02ccb2ff-da0e-44ce-9868-b73536d4bfc2",
                "Troca de tela do smartphone",
                BigDecimal.valueOf(250.00),
                EquipmentType.CELLPHONE
        );
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

    private Customer createCustomer() {
        return new Customer(
                "2bd3147b-e1bb-4a3f-973e-7d4b08e70c0c",
                "João Silva",
                "11912345678",
                "joao.silva@email.com",
                "12345678900 ",
                CustomerType.INDIVIDUAL
        );
    }

    private Technician createTechnician() {
        return new Technician(
                "39151ef3-2348-4f0d-95a8-b4f793780322",
                "Guilherme",
                "123456789",
                "guilherme@email.com");
    }

    private ServiceOrderTableDTO createServiceOrderTableDto(){
        return new ServiceOrderTableDTO(
                "5f7afbd9-cfe9-4859-8c45-4cf521547108",
                "Guilherme",
                LocalDate.now(),
                "Tela trincada",
                LocalDate.now().plusDays(3),
                ServiceOrderStatus.IN_PROGRESS
        );
    }
}