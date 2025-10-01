package com.eira.guilherme.technical_assistance.entrypoint.controller;

import com.eira.guilherme.technical_assistance.commom.mapper.ServiceOrderMapper;
import com.eira.guilherme.technical_assistance.commom.mapper.TechnicianMapper;
import com.eira.guilherme.technical_assistance.entrypoint.dto.service_order.ServiceOrderResponseDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.technician.TechnicianPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.technician.TechnicianPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.technician.TechnicianResponseDTO;
import com.eira.guilherme.technical_assistance.core.business.TechnicianBusiness;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/technicians")
@Tag(name = "Técnicos", description = "Operações relacionadas a técnicos")
public class TechnicianController {

    @Autowired
    TechnicianBusiness business;

    @Autowired
    TechnicianMapper mapper;

    @Autowired
    ServiceOrderMapper serviceOrderMapper;

    @PostMapping
    @Operation(summary = "Cria um novo técnico")
    public ResponseEntity<TechnicianResponseDTO> createTechnician(@Valid @RequestBody TechnicianPostRequestDTO dto, UriComponentsBuilder uriBuilder) {
        var technician = business.createTechnician(mapper.toDomain(dto));
        var uri = uriBuilder.path("/technicians/{id}").buildAndExpand(technician.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(technician));
    }

    @GetMapping
    @Operation(summary = "Cadastra um novo técnico")
    public ResponseEntity<List<TechnicianResponseDTO>> getAllTechnicians(){
        var technicians = business.getAllTechnicians().stream().map(t -> mapper.toDto(t)).toList();
        return ResponseEntity.ok().body(technicians);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista todos os técnicos cadastrados")
    public ResponseEntity<TechnicianResponseDTO> getTechnicianById(@PathVariable String id){
        return ResponseEntity.ok().body(mapper.toDto(business.getTechnicianById(id)));
    }

    @GetMapping("/{id}/service-orders")
    @Operation(summary = "Lista as ordens de serviço associadas a um técnico")
    public ResponseEntity<List<ServiceOrderResponseDTO>> getServiceOrdersByTechnician(@PathVariable String id){
        var serviceOrderList = business.getServiceOrdersByTechnician(id);
        return ResponseEntity.ok(serviceOrderList.stream().map(so -> serviceOrderMapper.toDto(so)).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um técnico")
    public ResponseEntity<TechnicianResponseDTO> updateTechnician(@PathVariable String id, @Valid @RequestBody TechnicianPutRequestDTO update){
        return ResponseEntity.ok().body(mapper.toDto(business.updateTechnician(id, mapper.toDomain(update))));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um técnico")
    public ResponseEntity<Void> deleteTechnician(@PathVariable String id){
        business.deleteTechnician(id);
        return ResponseEntity.noContent().build();
    }
}
