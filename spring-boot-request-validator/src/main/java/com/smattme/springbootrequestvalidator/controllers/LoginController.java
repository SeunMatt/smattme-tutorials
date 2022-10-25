package com.smattme.springbootrequestvalidator.controllers;

import com.smattme.requestvalidator.RequestValidator;
import com.smattme.springbootrequestvalidator.requests.LoginRequest;
import com.smattme.springbootrequestvalidator.responses.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {


    @PostMapping("/auth/login")
    public ResponseEntity<GenericResponse> login(@RequestBody LoginRequest request) {

        Map<String, String> rules = new HashMap<>();
        rules.put("email", "required|email");
        rules.put("password", "required");

        List<String> errors = RequestValidator.validate(request, rules);
        if(!errors.isEmpty()) return ResponseEntity.badRequest().body(GenericResponse.genericValidationErrorsObj(errors));

        //otherwise all is well, process the request
        //userService.signUp()

        return ResponseEntity.ok(GenericResponse.generic200ResponseObj("Login successful"));

    }
}
