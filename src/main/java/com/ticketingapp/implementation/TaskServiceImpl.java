package com.ticketingapp.implementation;

import com.ticketingapp.dto.ProjectDTO;
import com.ticketingapp.dto.TaskDTO;
import com.ticketingapp.entity.Project;
import com.ticketingapp.entity.Task;
import com.ticketingapp.entity.User;
import com.ticketingapp.enums.Status;
import com.ticketingapp.mapper.MapperUtil;
import com.ticketingapp.mapper.ProjectMapper;
import com.ticketingapp.repository.TaskRepository;
import com.ticketingapp.repository.UserRepository;
import com.ticketingapp.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private MapperUtil mapperUtil;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, MapperUtil mapperUtil) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public TaskDTO findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(value -> mapperUtil.convert(value, new TaskDTO())).orElse(null);
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks
                .stream()
                .map(task -> mapperUtil.convert(task, new TaskDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public Task save(TaskDTO taskDTO) {
        taskDTO.setTaskStatus(Status.OPEN);
        taskDTO.setAssignedDate(LocalDate.now());
        Task task = mapperUtil.convert(taskDTO, new Task());
        return taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(taskDTO.getId());
        Task convertedTask = mapperUtil.convert(taskDTO, new Task());
        if(task.isPresent()) {
            convertedTask.setId(task.get().getId());
            convertedTask.setTaskStatus(task.get().getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
        }
        taskRepository.save(convertedTask);
    }

    @Override
    public void delete(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()) {
            task.get().setIsDeleted(true);
            taskRepository.save(task.get());
        }
    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {
        return taskRepository.totalNonCompleteTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompleteTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {
        List<TaskDTO> tasks = listAllByProject(projectDTO);
        tasks.forEach(task -> delete(task.getId()));
    }

    @Override
    public List<TaskDTO> listAllByProject(ProjectDTO projectDTO) {
        List<Task> tasks = taskRepository.findByProject(mapperUtil.convert(projectDTO, new Project()));
        return tasks
                .stream()
                .map(task -> mapperUtil.convert(task, new TaskDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        User user = userRepository.findByUserName("tom@gmail.com");
        List<Task> tasks = taskRepository.findByTaskStatusIsNotAndAssignedEmployee(status, user);
        return tasks
                .stream()
                .map(task -> mapperUtil.convert(task, new TaskDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByProjectManager() {
        User user = userRepository.findByUserName("john@gmail.com");
        List<Task> tasks = taskRepository.findAllByProjectAssignedManager(user);
        return tasks
                .stream()
                .map(task -> mapperUtil.convert(task, new TaskDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(taskDTO.getId());
        if (task.isPresent()) {
            task.get().setTaskStatus(taskDTO.getTaskStatus());
            taskRepository.save(task.get());
        }
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        User user = userRepository.findByUserName("tom@gmail.com");
        List<Task> tasks = taskRepository.findByTaskStatusAndAssignedEmployee(Status.COMPLETE, user);
        return tasks
                .stream()
                .map(task -> mapperUtil.convert(task, new TaskDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> readAllByEmployee(User employee) {
        List<Task> tasks = taskRepository.findAllByAssignedEmployee(employee);
        return tasks
                .stream()
                .map(task -> mapperUtil.convert(task, new TaskDTO()))
                .collect(Collectors.toList());
    }
}
