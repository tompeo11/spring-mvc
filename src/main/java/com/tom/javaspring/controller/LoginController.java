package com.tom.javaspring.controller;

import com.tom.javaspring.dao.UserDAO;
import com.tom.javaspring.dto.UserDto;
import com.tom.javaspring.entity.Role;
import com.tom.javaspring.entity.UserEntity;
import com.tom.javaspring.service.RoleService;
import com.tom.javaspring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserDAO userDAO;

    public LoginController(UserService userService, RoleService roleService, UserDAO userDAO) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDAO = userDAO;
    }


    @GetMapping("/showLoginPage")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showMyRegisterPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "register";
        }

        UserEntity user = userService.getByName(userDto.getUserName());
        UserEntity email = userService.getByEmail(userDto.getEmail());

        if (user != null) {
            model.addAttribute("userDto", userDto);
            result.rejectValue("userName", "user.userName", "Username da ton tai");
            return "register";
        }

        if (email != null) {
            model.addAttribute("userDto", userDto);
            result.rejectValue("email", "user.email", "Email da ton tai");
            return "register";
        }

        Set<Role> roles = new HashSet<>();
        Role role = roleService.getByName("ROLE_EMPLOYEE");
        roles.add(role);

        UserEntity userEntity = UserEntity.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .imageUrl("default.jpg")
                .roles(roles)
                .enabled(true)
                .build();

        userService.saveUser(userEntity);

        return "redirect:/showLoginPage?success=true";
    }
}
