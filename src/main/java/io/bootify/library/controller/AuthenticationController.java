package io.bootify.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.bootify.library.service.MemberService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    MemberService memberService;

    @RequestMapping
    public String authentication() {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login() {
        
        return "redirect:/dashboard";
    }
    
}
