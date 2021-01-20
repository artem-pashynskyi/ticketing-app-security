package com.ticketingapp.controller;

import com.ticketingapp.dto.UserDTO;
import com.ticketingapp.exception.TicketingAppException;
import com.ticketingapp.service.RoleService;
import com.ticketingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private RoleService roleService;
    private UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping({"/create", "/add", "/initialize"})
    public String createUser(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.listAllRoles());
        model.addAttribute("users", userService.listAllUsers());
        return "user/create";
    }

    @PostMapping("/create")
    public String insertUser(UserDTO user) {
        userService.save(user);
        return "redirect:/user/create";
    }

    @GetMapping("/edit/{username}")
    public String editUser(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.findByUserName(username));
        model.addAttribute("users", userService.listAllUsers());
        model.addAttribute("roles", roleService.listAllRoles());
        return "user/edit";
    }

    @PostMapping("/update/{username}")
    public String updateUser(UserDTO user) {
        userService.update(user);
        return "redirect:/user/create";
    }

    @GetMapping("/delete/{username}")
    private String deleteUser(@PathVariable("username") String username) throws TicketingAppException {
        userService.delete(username);
        return "redirect:/user/create";
    }

}
