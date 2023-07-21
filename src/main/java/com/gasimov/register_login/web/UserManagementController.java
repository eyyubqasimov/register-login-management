package com.gasimov.register_login.web;


import com.gasimov.register_login.model.User;
import com.gasimov.register_login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/user_management")
public class UserManagementController {

    private UserService userService;

    @Autowired
    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public String viewUserManagementPage(Model model) {
//        List<User> allUsers = userService.getAllUsers();
//        model.addAttribute("users", allUsers);
//        return "user_management";
//    }
@GetMapping("/user-management")
public String userManagement(Model model) {
    List<User> allUsers = userService.getAllUsers();
    model.addAttribute("user", allUsers);
    return "user_management";
}

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ExceptionHandler(Exception.class)
    public String deleteUserRole(@PathVariable("email") String email, @PathVariable("roleName") String roleName) {
        try {
            userService.deleteUserRole(email, roleName);
            return "redirect:/user_management";
        } catch (Exception e) {
            return "error";
        }
    }
}
