package com.ticketingapp.mapper;

import com.ticketingapp.dto.RoleDTO;
import com.ticketingapp.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    public RoleDTO convertToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
