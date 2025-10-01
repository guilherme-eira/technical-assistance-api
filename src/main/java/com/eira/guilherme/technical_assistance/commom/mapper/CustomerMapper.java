package com.eira.guilherme.technical_assistance.commom.mapper;

import com.eira.guilherme.technical_assistance.core.domain.Customer;
import com.eira.guilherme.technical_assistance.entrypoint.dto.customer.CustomerPostRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.customer.CustomerPutRequestDTO;
import com.eira.guilherme.technical_assistance.entrypoint.dto.customer.CustomerResponseDTO;
import com.eira.guilherme.technical_assistance.resources.database.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toDomain(CustomerPostRequestDTO dto){
        return new Customer(null, dto.name(), dto.phone(), dto.email(), dto.document(), dto.type());
    }

    public Customer toDomain(CustomerPutRequestDTO dto){
        return new Customer(null, dto.name(), dto.phone(), dto.email(), dto.document(), dto.type());
    }

    public Customer toDomain(CustomerEntity entity){
        return new Customer(entity.getId(), entity.getName(), entity.getPhone(), entity.getEmail(), entity.getDocument(), entity.getType());
    }

    public CustomerEntity toEntity(Customer domain) {
        return new CustomerEntity(domain.getId(), domain.getName(), domain.getPhone(), domain.getEmail(), domain.getDocument(), domain.getType());
    }

    public CustomerResponseDTO toDto(Customer domain) {
        return new CustomerResponseDTO(domain.getId(), domain.getName(), domain.getPhone(), domain.getEmail(), domain.getDocument(), domain.getType());
    }



}
