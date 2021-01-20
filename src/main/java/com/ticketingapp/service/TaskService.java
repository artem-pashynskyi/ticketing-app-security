package com.ticketingapp.service;

import com.ticketingapp.dto.ProjectDTO;
import com.ticketingapp.dto.TaskDTO;
import com.ticketingapp.entity.Task;
import com.ticketingapp.entity.User;
import com.ticketingapp.enums.Status;

import java.util.List;

public interface TaskService {
    TaskDTO findById(Long id);
    List<TaskDTO> listAllTasks();
    Task save(TaskDTO taskDTO);
    void update(TaskDTO taskDTO);
    void delete(Long id);
    int totalNonCompletedTasks(String projectCode);
    int totalCompletedTasks(String projectCode);
    void deleteByProject(ProjectDTO projectDTO);
    List<TaskDTO> listAllByProject(ProjectDTO projectDTO);
    List<TaskDTO> listAllTasksByStatusIsNot(Status status);
    List<TaskDTO> listAllTasksByProjectManager();
    void updateStatus(TaskDTO taskDTO);
    List<TaskDTO> listAllTasksByStatus(Status status);
    List<TaskDTO> readAllByEmployee(User user);
}
