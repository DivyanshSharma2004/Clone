package com.example.demo.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Retrieve the error status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            // Based on the status code, you can return the respective error page
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "public/error/404"; // 404 error page
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "public/error/403"; // 403 error page
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "public/error/500"; // 500 error page
            }
        }
        return "public/error/general"; // General error page
    }
}
