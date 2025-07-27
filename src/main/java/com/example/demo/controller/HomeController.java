package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "public/home/home"; // home.html
    }

    @GetMapping("/home/about")
    public String about() {
        return "public/home/about"; // about.html
    }

    @GetMapping("/home/contact")
    public String contact() {
        return "public/home/contact"; // contact.html
    }

    @GetMapping("/user/home")
    public String userHome(){return "private/home";} //user home redirect after login

//    @GetMapping("home/services")
//    public String services() {
//        return "private/services"; // services.html
//    }
}
