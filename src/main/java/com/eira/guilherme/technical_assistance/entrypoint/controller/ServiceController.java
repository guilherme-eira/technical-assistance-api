package com.eira.guilherme.technical_assistance.entrypoint.controller;

import com.eira.guilherme.technical_assistance.commom.mapper.ServiceMapper;
import com.eira.guilherme.technical_assistance.commom.mapper.ServiceOrderMapper;
import com.eira.guilherme.technical_assistance.core.business.ServiceBusiness;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service.ServicePostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service.ServicePutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service.ServiceResponseDTO;
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
@RequestMapping("/services")
@Tag(name = "Serviços", description = "Operações relacionadas a serviços")
public class ServiceController {

    @Autowired
    ServiceBusiness business;

    @Autowired
    ServiceMapper mapper;

    @Autowired
    ServiceOrderMapper serviceOrderMapper;

    @PostMapping
    @Operation(summary = "Cadastra um novo serviço")
    public ResponseEntity<ServiceResponseDTO> createService(@Valid @RequestBody ServicePostRequestDTO dto, UriComponentsBuilder uriBuilder){
        var service = business.createService(mapper.toDomain(dto));
        var uri = uriBuilder.path("/services/{id}").buildAndExpand(service.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(service));
    }

    @GetMapping
    @Operation(summary = "Lista todos os serviços cadastrados")
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices(){
        var services = business.getAllServices().stream().map(e -> mapper.toDto(e)).toList();
        return ResponseEntity.ok().body(services);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um serviço por ID")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable String id){
        return ResponseEntity.ok().body(mapper.toDto(business.getServiceById(id)));
    }

    @GetMapping("/{id}/service-orders")
    @Operation(summary = "Lista todas as ordens de serviço associadas a um serviço")
    public ResponseEntity<List<ServiceOrderResponseDTO>> getServiceOrdersByService(@PathVariable String id){
        var serviceOrderList = business.getServiceOrdersByService(id);
        return ResponseEntity.ok(serviceOrderList.stream().map(so -> serviceOrderMapper.toDto(so)).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza as informações de um serviço")
    public ResponseEntity<ServiceResponseDTO> updateService(@PathVariable String id, @Valid @RequestBody ServicePutRequestDTO dto){
        return ResponseEntity.ok().body(mapper.toDto(business.updateService(id, mapper.toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um serviço")
    public ResponseEntity<Void> deleteService(@PathVariable String id){
        business.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
