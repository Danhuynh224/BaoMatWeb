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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleError(HttpServletRequest request) {
        // Lấy thông tin lỗi từ request
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");
        Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");
        
        // Log thông tin lỗi an toàn
        if (statusCode != null) {
            System.out.println("Error occurred - Status: " + statusCode);
        }
        
        // Luôn trả về trang 404.jsp cho mọi loại lỗi
        return "client/error/404";
    }
} 