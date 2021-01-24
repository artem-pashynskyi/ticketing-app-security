package com.ticketingapp.implementation;

import com.ticketingapp.dto.ProjectDTO;
import com.ticketingapp.dto.UserDTO;
import com.ticketingapp.entity.Project;
import com.ticketingapp.entity.User;
import com.ticketingapp.enums.Status;
import com.ticketingapp.mapper.MapperUtil;
import com.ticketingapp.repository.ProjectRepository;
import com.ticketingapp.service.ProjectService;
import com.ticketingapp.service.TaskService;
import com.ticketingapp.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private MapperUtil mapperUtil;
    private UserService userService;
    private TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, MapperUtil mapperUtil, UserService userService, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return mapperUtil.convert(project, new ProjectDTO());
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projects = projectRepository.findAll(Sort.by("projectCode"));
        return projects
                .stream()
                .map(project -> mapperUtil.convert(project, new ProjectDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.OPEN);
        Project project = mapperUtil.convert(projectDTO, new Project());
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO projectDTO) {
        Project project = projectRepository.findByProjectCode(projectDTO.getProjectCode());
        Project convertedProject = mapperUtil.convert(projectDTO, new Project());
        convertedProject.setProjectStatus(project.getProjectStatus());
        convertedProject.setId(project.getId());
        projectRepository.save(convertedProject);
    }

    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        projectRepository.save(project);
        taskService.deleteByProject(mapperUtil.convert(project, new ProjectDTO()));
    }

    @Override
    public void complete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setProjectStatus(Status.COMPLETE);
        project.setComplete(true);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO currentUserDTO = userService.findByUserName(username);
        User user = mapperUtil.convert(currentUserDTO, new User());
        List<Project> projects = projectRepository.findByAssignedManager(user);
        return projects
                .stream()
                .map(project -> {
                    ProjectDTO projectDTO = mapperUtil.convert(project, new ProjectDTO());
                    projectDTO.setUnfinishedTasksCount(taskService.totalNonCompletedTasks(project.getProjectCode()));
                    projectDTO.setCompleteTasksCount(taskService.totalCompletedTasks(project.getProjectCode()));
                    return projectDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User user) {
        List<Project> projects = projectRepository.findByAssignedManager(user);
        return projects
                .stream()
                .map(project -> mapperUtil.convert(project, new ProjectDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompleteProjects() {
        return projectRepository.findAllByProjectStatusIsNot(Status.COMPLETE)
                .stream()
                .map(project -> mapperUtil.convert(project, new ProjectDTO()))
                .collect(Collectors.toList());
    }
}
