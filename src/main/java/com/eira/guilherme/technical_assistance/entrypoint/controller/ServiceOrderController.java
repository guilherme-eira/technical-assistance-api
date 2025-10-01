package com.eira.guilherme.technical_assistance.entrypoint.controller;

import com.eira.guilherme.technical_assistance.commom.mapper.ServiceOrderMapper;
import com.eira.guilherme.technical_assistance.core.business.ServiceOrderBusiness;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderResponseDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderTableDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/service-orders")
@Tag(name = "Ordens de Serviço", description = "Operações relacionadas a ordens de serviço")
public class ServiceOrderController {

    @Autowired
    ServiceOrderBusiness business;

    @Autowired
    ServiceOrderMapper mapper;

    @PostMapping
    @Operation(summary = "Cadastra uma nova ordem de serviço")
    public ResponseEntity<ServiceOrderResponseDTO> createServiceOrder(@Valid @RequestBody ServiceOrderPostRequestDTO dto, UriComponentsBuilder uriBuilder){
        var serviceOrder = business.createServiceOrder (mapper.toDomain(dto));
        var uri = uriBuilder.path("/service-orders/{id}").buildAndExpand(serviceOrder.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(serviceOrder));
    }

    @GetMapping
    @Operation(summary = "Lista todas as ordens de serviço")
    public ResponseEntity<List<ServiceOrderResponseDTO>> getAllServiceOrders(){
        var serviceOrders = business.getAllServiceOrders().stream().map(s -> mapper.toDto(s)).toList();
        return ResponseEntity.ok(serviceOrders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma ordem de serviço por ID")
    public ResponseEntity<ServiceOrderResponseDTO> getServiceOrderById(@PathVariable String id){
        return ResponseEntity.ok().body(mapper.toDto(business.getServiceOrderById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma ordem de serviço")
    public ResponseEntity<ServiceOrderResponseDTO> updateServiceOrder(@PathVariable String id, @Valid @RequestBody ServiceOrderPutRequestDTO dto){
        return ResponseEntity.ok().body(mapper.toDto(business.updateServiceOrder(id, mapper.toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma ordem de serviço")
    public ResponseEntity<Void> deleteServiceOrder(@PathVariable String id){
        business.deleteServiceOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/start")
    @Operation(summary = "Atualiza o status de uma ordem de serviço para 'IN_PROGRESS'")
    public ResponseEntity<Void> startServiceOrder(@PathVariable String id){
        business.start(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Atualiza o status de uma ordem de serviço para 'CANCELED'")
    public ResponseEntity<Void> cancelServiceOrder(@PathVariable String id){
        business.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Atualiza o status de uma ordem de serviço para 'COMPLETED'")
    public ResponseEntity<Void> completeServiceOrder(@PathVariable String id){
        business.complete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/table-data")
    @Operation(summary = "Lista as ordens de serviço em um formato simplificado")
    public ResponseEntity<List<ServiceOrderTableDTO>> getServiceOrdersForTable(){
        var serviceOrderList = business.getServiceOrdersForTable();
        return ResponseEntity.ok().body(serviceOrderList.stream().map(so -> new ServiceOrderTableDTO(so.getId(), so.getCustomerName(), so.getEntryDate(), so.getDefect(), so.getExitDate(), so.getStatus())).toList());
    }

    @GetMapping("/table-data/customer")
    @Operation(summary = "Busca as ordens de serviço por nome do cliente e retorna em um formato simplificado")
    public ResponseEntity<List<ServiceOrderTableDTO>> getServiceOrderByCustomerName(@RequestParam String name){
        var serviceOrderList = business.getServiceOrdersForTableByCustomerName(name);
        return ResponseEntity.ok().body(serviceOrderList.stream().map(so -> new ServiceOrderTableDTO(so.getId(), so.getCustomerName(), so.getEntryDate(), so.getDefect(), so.getExitDate(), so.getStatus())).toList());
    }
}
