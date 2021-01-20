package com.ticketingapp.service;

import com.ticketingapp.dto.UserDTO;
import com.ticketingapp.entity.User;
import com.ticketingapp.exception.TicketingAppException;

import java.util.List;

public interface UserService {
    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    void delete(String username) throws TicketingAppException;
    void deleteByUserName(String username);
    List<UserDTO> listAllByRole(String role);
    Boolean canDelete(User user);
}
