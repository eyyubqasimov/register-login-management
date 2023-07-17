package com.gasimov.register_login.web;


import com.gasimov.register_login.model.User;
import com.gasimov.register_login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    private UserService userService;

    @Autowired
    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewUserManagementPage(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "user_management";
    }

    @PostMapping("/delete/{email}/{roleName}")
    public String deleteUserRole(@PathVariable("email") String email, @PathVariable("roleName") String roleName) {
        userService.deleteUserRole(email, roleName);
        return "redirect:/admin/users";
    }
}
