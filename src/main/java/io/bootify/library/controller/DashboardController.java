package io.bootify.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    @RequestMapping
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("librarian") == null) {
            return "redirect:/authentication";
        }
        return "dashboard";
    }
}
