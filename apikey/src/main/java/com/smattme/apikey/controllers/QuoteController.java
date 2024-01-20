package com.smattme.apikey.controllers;

import com.smattme.apikey.config.Routes;
import com.smattme.apikey.response.Response;
import com.smattme.apikey.services.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(Routes.QUOTES)
    public ResponseEntity<Response<Map<String, Object>>> famousQuotes() {
        var response = quoteService.chuckNorrisFacts();
        return ResponseEntity.status(response.getCode())
                .body(response);
    }

}
