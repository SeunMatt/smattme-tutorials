package com.smattme.springbootexceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class IndexController {

    @GetMapping("/ex/runtime")
    public ResponseEntity<Map<String, Object>> runtimeException() {
        throw new RuntimeException("RuntimeException raised");
    }

    @PostMapping("/ex/custom")
    public ResponseEntity<Map<String, Object>> customException(@RequestBody Map<String, Object> request) {
        List<String> errors = new ArrayList<>();
        if(!request.containsKey("username"))
            errors.add("Username is required");
        if(!request.containsKey("password"))
            errors.add("Password is required");

        if(!errors.isEmpty()) {
            String errorMessage = "Missing required parameters";
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, errorMessage , errors);
        }

        return ResponseEntity.ok(Collections.singletonMap("status", true));
    }

}
