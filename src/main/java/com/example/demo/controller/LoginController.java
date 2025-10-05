package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // You can add an attribute here that will be used in the Thymeleaf template
        model.addAttribute("message", "Please enter your credentials.");
        return "public/login";  // Returns the Thymeleaf template
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Add your authentication logic here
        if (username.equals("admin") && password.equals("password")) {
            return "redirect:/home";  // Redirect to home page on successful login
        }
        model.addAttribute("message", "Invalid credentials, please try again.");
        return "public/login";  // Redirect back to login page if authentication fails
    }
    //Added controller for when user session is expired
    @GetMapping("/login?expired=true")
    public String sessionExpired(Model model) {
        model.addAttribute("message", "Your session has expired. Please log in again.");
        return "login";
    }
    //Added controller for when user session is invalid
    @GetMapping("/login?invalid=true")
    public String invalidSession(Model model) {
        model.addAttribute("message", "Invalid session. Please log in again.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext(); // Clear authentication
        httpSession.invalidate(); // Invalidate session
        return "redirect:/login?logout"; // Redirect user after logout
    }
}