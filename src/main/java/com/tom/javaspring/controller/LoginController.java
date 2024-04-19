package com.tom.javaspring.controller;

import com.tom.javaspring.dao.UserDAO;
import com.tom.javaspring.dto.RegisteredUser;
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
    private final UserDAO userDAO;

    public LoginController(UserService userService, UserDAO userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }


    @GetMapping("/showLoginPage")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showMyRegisterPage(Model model) {
        RegisteredUser registeredUser = new RegisteredUser();
        model.addAttribute("registeredUser", registeredUser);
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute @Valid RegisteredUser registeredUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registeredUser", registeredUser);
            return "register";
        }

        UserEntity user = userService.getByName(registeredUser.getUserName());
        UserEntity email = userService.getByEmail(registeredUser.getEmail());

        if (user != null) {
            model.addAttribute("registeredUser", registeredUser);
            result.rejectValue("userName", "user.userName", "Username da ton tai");
            return "register";
        }

        if (email != null) {
            model.addAttribute("registeredUser", registeredUser);
            result.rejectValue("email", "user.email", "Email da ton tai");
            return "register";
        }

        UserEntity userEntity = UserEntity.builder()
                        .userName(registeredUser.getUserName())
                        .password(registeredUser.getPassword())
                        .email(registeredUser.getEmail())
                        .enabled(true)
                        .build();

        userService.saveUser(userEntity);

        return "redirect:/showLoginPage?success=true";
    }
}
