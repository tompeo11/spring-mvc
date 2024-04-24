package com.tom.javaspring.controller;

import com.tom.javaspring.dto.RoleParams;
import com.tom.javaspring.entity.Customer;
import com.tom.javaspring.entity.Role;
import com.tom.javaspring.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public String listRoles(@ModelAttribute RoleParams roleParams, Model model) {
        List<Role> roles = roleService.getRoles(roleParams);
        model.addAttribute("roleParams", roleParams);
        model.addAttribute("roles", roles);

        int rolesCount = roleService.getRoleCount(roleParams);
        int totalPages = (int) Math.ceil((double) rolesCount / roleParams.getPageSize());

        model.addAttribute("totalCount", rolesCount);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("roles", roles);

        return "/role/list_role";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        model.addAttribute("isUpdate", false);

        return "/role/form_role";
    }

    @PostMapping("/save")
    public String saveRole(@ModelAttribute("role") Role role, RedirectAttributes attributes) {
        if (role.getId() != 0) {
            attributes.addFlashAttribute("message", "Update success");
        } else {
            attributes.addFlashAttribute("message", "Add new success");
        }

        roleService.saveRole(role);

        return "redirect:/role/list";
    }

    @GetMapping("/edit/{id}")
    public String updateRole(Model model, @PathVariable int id) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        model.addAttribute("isUpdate", true);

        return "/role/form_role";
    }

    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable int id, RedirectAttributes attributes) {
        roleService.deleteRole(id);
        attributes.addFlashAttribute("message", "Delete success");


        return "redirect:/role/list";
    }

    @GetMapping("/view/{id}")
    public String viewRoles(@ModelAttribute RoleParams roleParams, Model model, @PathVariable int id) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);

        return "/role/view_role";
    }
}
