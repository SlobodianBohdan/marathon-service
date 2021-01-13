package com.softserve.marathon.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @PostMapping("/logout")
    public void logOut (){
        SecurityContextHolder.clearContext();
    }
}