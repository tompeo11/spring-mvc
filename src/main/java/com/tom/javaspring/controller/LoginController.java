package com.tom.javaspring.controller;

import com.tom.javaspring.entity.UserEntity;
import com.tom.javaspring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/showLoginPage")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showMyRegisterPage(Model model) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEnabled("1");
        model.addAttribute("userEntity", userEntity);
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute @Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userEntity", userEntity);
            return "register";
        }

        userService.saveUser(userEntity);

        return "redirect:/showLoginPage";
    }
}
