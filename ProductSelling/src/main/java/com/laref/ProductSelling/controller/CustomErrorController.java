package com.laref.ProductSelling.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Unknown error";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("statusCode", statusCode);

            if (statusCode == 404) {
                errorMessage = "Page not found";
            } else if (statusCode == 500) {
                errorMessage = "Internal server error";
            } else {
                errorMessage = "Unexpected error occurred";
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }


    public String getErrorPath() {
        return "/error";
    }
}
