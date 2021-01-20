package com.ticketingapp.controller;

import com.ticketingapp.dto.ProjectDTO;
import com.ticketingapp.enums.Status;
import com.ticketingapp.service.ProjectService;
import com.ticketingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createProject(Model model) {
        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("managers", userService.listAllByRole("manager"));
        return "project/create";
    }

    @PostMapping("/create")
    public String insertProject(ProjectDTO project) {
        projectService.save(project);
        project.setProjectStatus(Status.OPEN);
        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectCode);
        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectcode}")
    public String completeProject(@PathVariable("projectcode") String projectcode) {
        projectService.complete(projectcode);
        return "redirect:/project/create";
    }

    @GetMapping("/edit/{projectcode}")
    public String editProject(@PathVariable("projectcode") String projectcode, Model model) {
        model.addAttribute("project", projectService.getByProjectCode(projectcode));
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("managers", userService.listAllByRole("manager"));
        return "project/edit";
    }

    @PostMapping("/update/{projectcode}")
    public String updateProject(ProjectDTO project) {
        projectService.update(project);
        return "redirect:/project/create";
    }

    @GetMapping("/manager/complete")
    public String getProjectsByManager(Model model) {
        List<ProjectDTO> projects = projectService.listAllProjectDetails();
        model.addAttribute("projects", projects);
        return "manager/project-status";
    }

    @GetMapping("/manager/complete/{projectcode}")
    public String completeProjectForManagerById(@PathVariable("projectcode") String projectcode) {
        projectService.complete(projectcode);
        return "redirect:/project/manager/complete";
    }

}
