package com.ticketingapp.implementation;

import com.ticketingapp.dto.ProjectDTO;
import com.ticketingapp.dto.TaskDTO;
import com.ticketingapp.dto.UserDTO;
import com.ticketingapp.entity.User;
import com.ticketingapp.exception.TicketingAppException;
import com.ticketingapp.mapper.MapperUtil;
import com.ticketingapp.repository.UserRepository;
import com.ticketingapp.service.ProjectService;
import com.ticketingapp.service.TaskService;
import com.ticketingapp.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private MapperUtil mapperUtil;
    private ProjectService projectService;
    private TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userEntities = userRepository.findAll(Sort.by("firstName"));
        return userEntities
                .stream()
                .map(user -> mapperUtil.convert(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserName(username);
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public void save(UserDTO userDTO) {
        userRepository.save(mapperUtil.convert(userDTO, new User()));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userRepository.findByUserName(userDTO.getUserName());
        User convertedUser = mapperUtil.convert(userDTO, new User());
        convertedUser.setId(user.getId());
        userRepository.save(convertedUser);
        return findByUserName(userDTO.getUserName());
    }

    @Override
    public void delete(String username) throws TicketingAppException {
        User user = userRepository.findByUserName(username);
        if(user == null)
            throw new TicketingAppException("User does not exist!");
        if(!canDelete(user)) {
            throw new TicketingAppException("User can not be deleted. It is linked by project or task");
        }
        user.setUserName(user.getUserName() + "-" + user.getId());
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    //Hard Delete
    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> usersByRole = userRepository.findByRoleDescriptionIgnoreCase(role);
        return usersByRole
                .stream()
                .map(user -> mapperUtil.convert(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean canDelete(User user) {
        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projects = projectService.readAllByAssignedManager(user);
                return projects.size() == 0;
            case "Employee":
                List<TaskDTO> taks = taskService.readAllByEmployee(user);
                return taks.size() == 0;
            default:
                return true;
        }
    }
}
