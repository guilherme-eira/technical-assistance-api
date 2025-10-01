package com.eira.guilherme.technical_assistance.core.business.impl;

import com.eira.guilherme.technical_assistance.commom.exception.BusinessException;
import com.eira.guilherme.technical_assistance.core.domain.Customer;
import com.eira.guilherme.technical_assistance.core.domain.ServiceOrder;
import com.eira.guilherme.technical_assistance.core.domain.enums.CustomerType;
import com.eira.guilherme.technical_assistance.core.service.CustomerService;
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
class CustomerBusinessImplTest {

    @InjectMocks
    CustomerBusinessImpl business;

    @Mock
    CustomerService service;

    @Mock
    ServiceOrderService serviceOrderService;

    private final String ID = "2bd3147b-e1bb-4a3f-973e-7d4b08e70c0c";

    @Test
    void shouldCreateCustomer() {
        var customer = createCustomer();

        BDDMockito.given(service.nameAlreadyInUse(customer.getName())).willReturn(false);
        BDDMockito.given(service.phoneAlreadyInUse(customer.getPhone())).willReturn(false);
        BDDMockito.given(service.emailAlreadyInUse(customer.getEmail())).willReturn(false);
        BDDMockito.given(service.documentAlreadyInUse(customer.getDocument())).willReturn(false);

        BDDMockito.given(service.saveCustomer(customer)).willReturn(customer);

        var savedCustomer = business.createCustomer(customer);

        Assertions.assertEquals(customer.getId(), savedCustomer.getId());
        Assertions.assertEquals(customer.getName(), savedCustomer.getName());
        Assertions.assertEquals(customer.getPhone(), savedCustomer.getPhone());
        Assertions.assertEquals(customer.getEmail(), savedCustomer.getEmail());
        Assertions.assertEquals(customer.getDocument(), savedCustomer.getDocument());
    }

    @Test
    void shouldReturnAllCustomers() {
        var customer = createCustomer();
        BDDMockito.given(service.getAllCustomers()).willReturn(List.of(customer));

        var customerList = business.getAllCustomers();

        Assertions.assertEquals(1, customerList.size());
        Assertions.assertEquals(customer.getId(), customerList.getFirst().getId());
        Assertions.assertEquals(customer.getName(), customerList.getFirst().getName());
    }

    @Test
    void shouldReturnCustomerById() {
        var customer = createCustomer();
        BDDMockito.given(service.getCustomerById(customer.getId())).willReturn(customer);

        var foundCustomer = business.getCustomerById(customer.getId());

        Assertions.assertEquals(customer.getId(), foundCustomer.getId());
        Assertions.assertEquals(customer.getName(), foundCustomer.getName());
    }

    @Test
    void shouldUpdateCustomer() {
        var original = createCustomer();
        var update = new Customer(null, "Maria Luiza", "11984375988", "maria@email.com", "43865098230", null);

        BDDMockito.given(service.nameAlreadyInUse(update.getName())).willReturn(false);
        BDDMockito.given(service.phoneAlreadyInUse(update.getPhone())).willReturn(false);
        BDDMockito.given(service.emailAlreadyInUse(update.getEmail())).willReturn(false);
        BDDMockito.given(service.documentAlreadyInUse(update.getDocument())).willReturn(false);

        BDDMockito.given(service.getCustomerById(original.getId())).willReturn(original);

        var updated = original.updateCustomer(update);
        BDDMockito.given(service.saveCustomer(updated)).willReturn(updated);

        var savedCustomer = business.updateCustomer(original.getId(), update);

        Assertions.assertEquals(updated.getId(), savedCustomer.getId());
        Assertions.assertEquals(updated.getName(), savedCustomer.getName());
        Assertions.assertEquals(updated.getPhone(), savedCustomer.getPhone());
        Assertions.assertEquals(updated.getEmail(), savedCustomer.getEmail());
        Assertions.assertEquals(updated.getDocument(), savedCustomer.getDocument());
    }

    @Test
    void shouldDeleteCustomer() {
        var customer = createCustomer();

        business.deleteCustomer(customer.getId());

        then(service).should().deleteCustomer(customer.getId());
    }

    @Test
    void shouldReturnServiceOrdersByCustomer() {
        var customer = createCustomer();
        var serviceOrder = new ServiceOrder.Builder()
                .withId("5f7afbd9-cfe9-4859-8c45-4cf521547108")
                .withCustomerId(customer.getId())
                .build();

        BDDMockito.given(serviceOrderService.getServiceOrdersByCustomer(customer.getId()))
                .willReturn(List.of(serviceOrder));

        var serviceOrders = business.getServiceOrdersByCustomer(customer.getId());

        Assertions.assertEquals(1, serviceOrders.size());
        Assertions.assertEquals(serviceOrder.getId(), serviceOrders.getFirst().getId());
    }

    @Test
    void shouldThrowBusinessExceptionIfNameAlreadyExistsOnCreate() {
        var customer = createCustomer();
        BDDMockito.given(service.nameAlreadyInUse(customer.getName())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.createCustomer(customer));
    }

    @Test
    void shouldThrowBusinessExceptionIfPhoneAlreadyExistsOnCreate() {
        var customer = createCustomer();
        BDDMockito.given(service.phoneAlreadyInUse(customer.getPhone())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.createCustomer(customer));
    }

    @Test
    void shouldThrowBusinessExceptionIfEmailAlreadyExistsOnCreate() {
        var customer = createCustomer();
        BDDMockito.given(service.emailAlreadyInUse(customer.getEmail())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.createCustomer(customer));
    }

    @Test
    void shouldThrowBusinessExceptionIfDocumentAlreadyExistsOnCreate() {
        var customer = createCustomer();
        BDDMockito.given(service.documentAlreadyInUse(customer.getDocument())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.createCustomer(customer));
    }

    @Test
    void shouldThrowBusinessExceptionIfNameAlreadyExistsOnUpdate() {
        var customer = createCustomer();
        BDDMockito.given(service.nameAlreadyInUse(customer.getName())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.updateCustomer(ID, customer));
    }

    @Test
    void shouldThrowBusinessExceptionIfPhoneAlreadyExistsOnUpdate() {
        var customer = createCustomer();
        BDDMockito.given(service.phoneAlreadyInUse(customer.getPhone())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.updateCustomer(ID, customer));
    }

    @Test
    void shouldThrowBusinessExceptionIfEmailAlreadyExistsOnUpdate() {
        var customer = createCustomer();
        BDDMockito.given(service.emailAlreadyInUse(customer.getEmail())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.updateCustomer(ID, customer));
    }

    @Test
    void shouldThrowBusinessExceptionIfDocumentAlreadyExistsOnUpdate() {
        var customer = createCustomer();
        BDDMockito.given(service.documentAlreadyInUse(customer.getDocument())).willReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> business.updateCustomer(ID, customer));
    }

    private Customer createCustomer() {
        return new Customer(
                ID,
                "João Silva",
                "11912345678",
                "joao.silva@email.com",
                "12345678900 ",
                CustomerType.INDIVIDUAL
        );
    }
}