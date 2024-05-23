package io.bootify.library.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import io.bootify.library.model.LibrarianDTO;
import io.bootify.library.service.LibrarianService;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    LibrarianService librarianService;

    @RequestMapping
    public String authentication(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("librarianDTO", new LibrarianDTO());
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LibrarianDTO librarianDTO, RedirectAttributes redirectAttributes, HttpSession Session) {

        try {
            librarianService.login(librarianDTO);
            Session.setAttribute("librarian", librarianDTO);

        } catch (Exception e) {
            //Set error message in a model
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/authentication";
        }
        return "redirect:/books";
    }
    
}
