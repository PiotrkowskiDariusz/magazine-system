package com.dariuszpiotrkowski.magazineSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError() {
        return "access-denied.html";
    }

}
