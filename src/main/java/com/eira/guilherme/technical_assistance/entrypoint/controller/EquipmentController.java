package com.eira.guilherme.technical_assistance.entrypoint.controller;

import com.eira.guilherme.technical_assistance.commom.mapper.EquipmentMapper;
import com.eira.guilherme.technical_assistance.commom.mapper.ServiceOrderMapper;
import com.eira.guilherme.technical_assistance.entrypoint.dto.equipment.EquipmentPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.equipment.EquipmentPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.equipment.EquipmentResponseDTO;
import com.eira.guilherme.technical_assistance.core.business.EquipmentBusiness;
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
@RequestMapping("/equipments")
@Tag(name = "Equipamentos", description = "Operações relacionadas a equipamentos")
public class EquipmentController {

    @Autowired
    EquipmentBusiness business;

    @Autowired
    EquipmentMapper mapper;

    @Autowired
    ServiceOrderMapper serviceOrderMapper;

    @PostMapping
    @Operation(summary = "Cadastra um novo equipamento")
    public ResponseEntity<EquipmentResponseDTO> createEquipment(@Valid @RequestBody EquipmentPostRequestDTO dto, UriComponentsBuilder uriBuilder){
        var equipment = business.createEquipment(mapper.toDomain(dto));
        var uri = uriBuilder.path("/equipments/{id}").buildAndExpand(equipment.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(equipment));
    }

    @GetMapping
    @Operation(summary = "Lista todos os equipamentos")
    public ResponseEntity<List<EquipmentResponseDTO>> getAllEquipments(){
        var equipments = business.getAllEquipments().stream().map(e -> mapper.toDto(e)).toList();
        return ResponseEntity.ok().body(equipments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um equipamento por ID")
    public ResponseEntity<EquipmentResponseDTO> getEquipmentById(@PathVariable String id){
        return ResponseEntity.ok().body(mapper.toDto(business.getEquipmentById(id)));
    }

    @GetMapping("/{id}/service-orders")
    @Operation(summary = "Lista ordens de serviço associadas a um equipamento")
    public ResponseEntity<List<ServiceOrderResponseDTO>> getServiceOrdersByEquipment(@PathVariable String id){
        var serviceOrderList = business.getServiceOrdersByEquipment(id);
        return ResponseEntity.ok(serviceOrderList.stream().map(so -> serviceOrderMapper.toDto(so)).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza as informações de um equipamento")
    public ResponseEntity<EquipmentResponseDTO> updateEquipment(@PathVariable String id, @Valid @RequestBody EquipmentPutRequestDTO dto){
        return ResponseEntity.ok().body(mapper.toDto(business.updateEquipment(id, mapper.toDomain(dto))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um equipamento")
    public ResponseEntity<Void> deleteEquipment(@PathVariable String id){
        business.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
