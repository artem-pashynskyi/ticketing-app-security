package com.ticketingapp.implementation;

import com.ticketingapp.dto.RoleDTO;
import com.ticketingapp.entity.Role;
import com.ticketingapp.mapper.RoleMapper;
import com.ticketingapp.repository.RoleRepository;
import com.ticketingapp.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        List<Role> rolesEntity = roleRepository.findAll();
        return rolesEntity
                .stream()
                .map(role -> roleMapper.convertToDTO(role))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.convertToDTO(roleRepository.findById(id).get());
    }
}
