package com.smattme.springboot.hmacsignature.controllers;

import com.smattme.springboot.hmacsignature.config.Routes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping(Routes.INDEX)
    public ResponseEntity<String> index() {
      return ResponseEntity.ok("Hello World");
    }
}
