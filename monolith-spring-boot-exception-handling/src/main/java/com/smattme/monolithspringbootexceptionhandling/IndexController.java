package com.smattme.monolithspringbootexceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/ex/runtime")
    public String runtimeEx() {
        throw new RuntimeException("Runtime Exception");
    }

    @GetMapping("/ex/custom")
    public ResponseEntity<Map<String, Object>> customException(@RequestParam Map<String, Object> request) {
        List<String> errors = new ArrayList<>();
        if(!request.containsKey("username"))
            errors.add("Username is required");

        if(!errors.isEmpty()) {
            String errorMessage = "Missing required parameters";
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, errorMessage , errors);
        }

        return ResponseEntity.ok(Collections.singletonMap("status", true));
    }
}
