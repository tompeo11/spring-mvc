package com.tom.javaspring.controller;

import com.tom.javaspring.dto.RoleParams;
import com.tom.javaspring.dto.UserParams;
import com.tom.javaspring.entity.Role;
import com.tom.javaspring.entity.UserEntity;
import com.tom.javaspring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUsers(@ModelAttribute UserParams userParams, Model model) {
        List<UserEntity> users = userService.getUsers(userParams);
        model.addAttribute("userParams", userParams);
        model.addAttribute("users", users);

        int userCount = userService.getUserCount(userParams);
        int totalPages = (int) Math.ceil((double) userCount / userParams.getPageSize());

        model.addAttribute("totalCount", userCount);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("users", users);

        return "/user/list_user";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        model.addAttribute("isUpdate", false);

        return "/user/form_user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserEntity user, RedirectAttributes attributes) {
        if (user.getId() != 0) {
            attributes.addFlashAttribute("message", "Update success");
        } else {
            attributes.addFlashAttribute("message", "Add new success");
        }

        userService.saveUser(user);

        return "redirect:/user/list";
    }

    @GetMapping("/edit/{id}")
    public String updateUser(Model model, @PathVariable int id) {
        UserEntity user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("isUpdate", true);

        return "/user/form_user";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, RedirectAttributes attributes) {
        userService.deleteUser(id);
        attributes.addFlashAttribute("message", "Delete success");

        return "redirect:/user/list";
    }

    @GetMapping("/view/{id}")
    public String viewUsers(@ModelAttribute UserParams userParams, Model model, @PathVariable int id) {
        UserEntity user = userService.getById(id);
        model.addAttribute("user", user);

        return "/user/view_user";
    }
}
