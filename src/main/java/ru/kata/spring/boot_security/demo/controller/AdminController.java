package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("newUser", userService.createUser());
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "/new";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String updateUserForm(@PathVariable("id")Long id, Model model) {
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "/edit";
    }

    @PutMapping ("/edit/{id}")
    public String updateUser(@ModelAttribute("user")User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
