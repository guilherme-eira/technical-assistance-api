package com.eira.guilherme.technical_assistance.entrypoint.controller;

import com.eira.guilherme.technical_assistance.commom.mapper.CustomerMapper;
import com.eira.guilherme.technical_assistance.commom.mapper.ServiceOrderMapper;
import com.eira.guilherme.technical_assistance.core.business.CustomerBusiness;
import com.eira.guilherme.technical_assistance.entrypoint.dto.customer.CustomerPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.customer.CustomerPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.customer.CustomerResponseDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class CustomerController {

    @Autowired
    CustomerBusiness business;

    @Autowired
    CustomerMapper mapper;

    @Autowired
    ServiceOrderMapper serviceOrderMapper;

    @PostMapping
    @Operation(summary = "Cadastra um novo cliente")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerPostRequestDTO dto, UriComponentsBuilder uriBuilder){
        var customer = business.createCustomer(mapper.toDomain(dto));
        var uri = uriBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(customer));
    }

    @GetMapping
    @Operation(summary = "Lista todos os clientes cadastrados")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        var customers = business.getAllCustomers();
        return ResponseEntity.ok().body(customers.stream().map(c -> mapper.toDto(c)).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cliente por ID")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable String id){
        return ResponseEntity.ok().body(mapper.toDto(business.getCustomerById(id)));
    }

    @GetMapping("/{id}/service-orders")
    @Operation(summary = "Lista ordens de serviço associadas a um cliente")
    public ResponseEntity<List<ServiceOrderResponseDTO>> getServiceOrdersByCustomer(@PathVariable String id){
        var serviceOrderList = business.getServiceOrdersByCustomer(id);
        return ResponseEntity.ok(serviceOrderList.stream().map(so -> serviceOrderMapper.toDto(so)).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza as informações de um cliente")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerPutRequestDTO dto){
        return ResponseEntity.ok().body(mapper.toDto(business.updateCustomer(id, mapper.toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um cliente")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
        business.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
