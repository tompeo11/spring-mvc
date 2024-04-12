package com.tom.javaspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String showMyLoginPage() {
        return "/layout/master";
    }

    @GetMapping("/unauthorized")
    public String showAccessDenied() {
        return "access_denied";
    }
}
