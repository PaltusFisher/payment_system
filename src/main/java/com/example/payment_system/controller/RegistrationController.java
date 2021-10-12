package com.example.payment_system.controller;

import com.example.payment_system.entity.Role;
import com.example.payment_system.entity.User;
import com.example.payment_system.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {
    RegistrationController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Model model,
                          @RequestParam String email,
                          @RequestParam String phoneNumber) {

        String password =  userService.generateRandomPassword(20);
        User user = new User(phoneNumber,
                             email,
                             password,
                        100,
                       true);


        String message = userService.validateRegistrationData(user, email, phoneNumber);
        if (message != null)
        {
            model.addAttribute("message", message);
            return "registration";
        }

        user.setRoles(Collections.singleton(Role.USER));
        userService.userSave(user);
        model.addAttribute("password", password);
        return "show-password-page";
    }

    @GetMapping("/show-password-page")
    public String showPasswordPage() {
        return "show-password-page";
    }
}
