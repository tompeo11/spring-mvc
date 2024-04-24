package com.tom.javaspring.controller;

import com.tom.javaspring.dto.UserParams;
import com.tom.javaspring.entity.Role;
import com.tom.javaspring.entity.UserEntity;
import com.tom.javaspring.service.RoleService;
import com.tom.javaspring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final ServletContext servletContext;

    public UserController(UserService userService, RoleService roleService, ServletContext servletContext) {
        this.userService = userService;
        this.roleService = roleService;
        this.servletContext = servletContext;
    }

    @GetMapping("/list")
    public String listUsers(@ModelAttribute UserParams userParams, Model model) {
        List<UserEntity> users = userService.getUsers(userParams);
        List<Role> roles = roleService.getRoles();
        model.addAttribute("userParams", userParams);
        model.addAttribute("users", users);

        int userCount = userService.getUserCount(userParams);
        int totalPages = (int) Math.ceil((double) userCount / userParams.getPageSize());

        model.addAttribute("totalCount", userCount);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "/user/list_user";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        UserEntity user = new UserEntity();
        List<Role> roles = roleService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("isUpdate", false);
        model.addAttribute("roles", roles);

        return "/user/form_user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserEntity user,
                           @RequestParam MultipartFile image,
                           RedirectAttributes attributes,
                           Model model,
                           BindingResult result) {
        MultipartFile img = image;

        try {
            if (img != null && !img.isEmpty()) {
                String uploadDir = "/resources/images/user";
                String realUploadDir = servletContext.getRealPath(uploadDir);

                Path uploadPath = Paths.get(realUploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String filename =  System.currentTimeMillis() + "-" + img.getOriginalFilename();

                Path filePath = uploadPath.resolve(filename);
                img.transferTo(filePath.toFile());

                user.setImageUrl(filename);
            }
        } catch (IOException e) {
            model.addAttribute("user", user);
            result.rejectValue("image", "user.image", "Error saving image!");
            return "user/list_user";
        }

        Set<Role> roles = user.getRoleString().stream()
                .map(roleService::getByName)
                .collect(Collectors.toSet());

        user.setRoles(roles);

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
        List<Role> roles = roleService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("isUpdate", true);
        model.addAttribute("roles", roles);

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
