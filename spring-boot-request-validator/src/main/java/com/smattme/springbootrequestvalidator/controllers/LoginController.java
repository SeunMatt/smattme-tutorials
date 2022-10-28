package com.smattme.springbootrequestvalidator.controllers;

import com.smattme.requestvalidator.RequestValidator;
import com.smattme.springbootrequestvalidator.requests.LoginRequest;
import com.smattme.springbootrequestvalidator.responses.GenericResponse;
import org.springframework.http.HttpStatus;
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
        if (!errors.isEmpty()) {
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setStatus(false);
            genericResponse.setCode(HttpStatus.BAD_REQUEST.value());
            genericResponse.setErrors(errors);
            genericResponse.setMessage("Missing required parameter(s)");
            return ResponseEntity.badRequest().body(genericResponse);
        }

        //otherwise all is well, process the request
        //loginService.login()

        return ResponseEntity.ok(GenericResponse.generic200ResponseObj("Login successful"));

    }
}
