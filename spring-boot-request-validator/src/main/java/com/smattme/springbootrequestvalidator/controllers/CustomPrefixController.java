package com.smattme.springbootrequestvalidator.controllers;

import com.smattme.springbootrequestvalidator.customvalidator.CustomRequestValidator;
import com.smattme.springbootrequestvalidator.responses.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class CustomPrefixController {

    @PostMapping("/custom")
    public ResponseEntity<GenericResponse> formCustomPrefix(@RequestBody Map<String, Object> request) {

        Map<String, String> rules = Collections.singletonMap("objectType", "customprefix");

        List<String> errors = CustomRequestValidator.validate(request, rules);
        if(!errors.isEmpty()) {
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setStatus(false);
            genericResponse.setCode(HttpStatus.BAD_REQUEST.value());
            genericResponse.setErrors(errors);
            genericResponse.setMessage("Missing required parameter(s)");
            return ResponseEntity.badRequest().body(genericResponse);
        }

        return ResponseEntity.ok(GenericResponse.generic200ResponseObj("Operation successful"));
    }
}
