package com.smattme.springbootrequestvalidator.controllers;

import com.smattme.requestvalidator.RequestValidator;
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
public class UserController {


    @PostMapping("/auth/signup")
    public ResponseEntity<GenericResponse> signUp(@RequestBody Map<String, Object> request) {

        Map<String, String> rules = new HashMap<>();
        rules.put("firstName", "required|max:250");
        rules.put("lastName", "required|max:250");
        rules.put("email", "required|max:250|email");
        rules.put("dob", "required||regex:[0-9]{2}-[0-9]{2}-[0-9]{4}");
        rules.put("gender", "required|in:MALE,FEMALE");
        rules.put("interests", "optional|array");
        rules.put("preferences.emailNotificationEnabled", "optional|in:true,false");
        rules.put("preferences.frequency", "optional|digit");
        rules.put("kyc.idType", "required|in:BVN,SSN");
        rules.put("kyc.bvn", "requiredIf:kyc.idType,BVN|length:11");
        rules.put("kyc.ssn", "requiredIf:kyc.idType,SSN|length:12");
        rules.put("kyc.address", "optional|max:250");
        rules.put("investmentAmount", "optional|numeric");
        rules.put("investmentCurrency", "requiredWith:investmentAmount|in:USD,NGN");

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
        //userService.signUp()

        return ResponseEntity.ok(GenericResponse.generic200ResponseObj("Sing up successful"));

    }
}
