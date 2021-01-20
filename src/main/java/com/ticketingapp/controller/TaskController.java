package com.ticketingapp.controller;

import com.ticketingapp.dto.TaskDTO;
import com.ticketingapp.enums.Status;
import com.ticketingapp.service.ProjectService;
import com.ticketingapp.service.TaskService;
import com.ticketingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

    private ProjectService projectService;
    private UserService userService;
    private TaskService taskService;

    public TaskController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/create")
    public String createTask(Model model) {
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("employees", userService.listAllByRole("employee"));
        model.addAttribute("tasks", taskService.listAllTasks());
        return "task/create";
    }

    @PostMapping("/create")
    public String insertTask(TaskDTO task) {
        taskService.save(task);
        return "redirect:/task/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return "redirect:/task/create";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("employees", userService.listAllByRole("employee"));
        model.addAttribute("tasks", taskService.listAllTasks());
        return "task/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task) {
        taskService.update(task);
        return "redirect:/task/create";
    }

    @GetMapping("/employee/status")
    public String statusTask(Model model) {
        model.addAttribute("tasks", taskService.listAllTasksByStatusIsNot(Status.COMPLETE));
        return "employee/pending-task";
    }

    @GetMapping("/employee/status/edit/{id}")
    public String statusEditTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("tasks", taskService.listAllTasksByStatusIsNot(Status.COMPLETE));
        model.addAttribute("statuses", Status.values());
        return "employee/pending-task-edit";
    }

    @PostMapping("/employee/status/update/{id}")
    public String statusUpdateTask(TaskDTO taskDTO) {
        taskService.updateStatus(taskDTO);
        return "redirect:/task/employee/status";
    }

    @GetMapping("/employee/archive")
    public String archive(Model model) {
        model.addAttribute("tasks", taskService.listAllTasksByStatus(Status.COMPLETE));
        return "employee/archived-projects";
    }

}
