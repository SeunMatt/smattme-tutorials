package com.smattme.monolithspringbootexceptionhandling;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    @ExceptionHandler(CustomApplicationException.class)
    public String handCustomEx(CustomApplicationException ex, Model model) {
        model.addAttribute("code", ex.getHttpStatus().value());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errors", ex.getErrors());
        return "error/custom-app-error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        //do something custom and return a custom view
        model.addAttribute("whatnext", "Try again in " +
                LocalDateTime.now().plusMinutes(10));
        return "error/custom-error";
    }
}
