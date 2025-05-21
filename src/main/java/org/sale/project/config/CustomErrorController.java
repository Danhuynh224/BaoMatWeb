package org.sale.project.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Log error details for debugging (but don't show to user)
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");
        Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");
        
        // Log error details here if needed
        System.out.println("Error occurred - Status: " + statusCode + ", Message: " + errorMessage);
        if (exception != null) {
            System.out.println("Exception: " + exception.getMessage());
        }
        
        // Always return 404 page for any error
        return "client/error/404";
    }
} 