package com.smattme.apikey.controllers;

import com.smattme.apikey.config.Routes;
import com.smattme.apikey.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IndexController {

    @GetMapping(Routes.WEB_INDEX)
    public ResponseEntity<Response<Map<String, Long>>> index() {
        var response = Response.successfulResponse("Welcome to SMATTME",
                Map.of("timestamp", System.currentTimeMillis()));
        return ResponseEntity.status(response.getCode())
                .body(response);
    }
}
