package com.ticketingapp.service;

import com.ticketingapp.dto.ProjectDTO;
import com.ticketingapp.entity.User;

import java.util.List;

public interface ProjectService {
    ProjectDTO getByProjectCode(String code);
    List<ProjectDTO> listAllProjects();
    void save(ProjectDTO projectDTO);
    void update(ProjectDTO projectDTO);
    void delete(String code);
    void complete(String code);
    List<ProjectDTO> listAllProjectDetails();
    List<ProjectDTO> readAllByAssignedManager(User user);
}
