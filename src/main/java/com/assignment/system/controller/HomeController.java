package com.assignment.system.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_PROFESSOR")) {
                    return "redirect:/professor/dashboard";
                } else if (authority.getAuthority().equals("ROLE_STUDENT")) {
                    return "redirect:/student/dashboard";
                }
            }
        }
        return "redirect:/";
    }
}
