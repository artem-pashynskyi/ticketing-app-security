package com.ticketingapp.mapper;

import com.ticketingapp.dto.RoleDTO;
import com.ticketingapp.dto.UserDTO;
import com.ticketingapp.entity.Role;
import com.ticketingapp.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToDTO(User user) { return modelMapper.map(user, UserDTO.class); }

}
