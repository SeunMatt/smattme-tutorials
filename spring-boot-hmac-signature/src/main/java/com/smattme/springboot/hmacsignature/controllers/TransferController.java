package com.smattme.springboot.hmacsignature.controllers;

import com.smattme.springboot.hmacsignature.config.Routes;
import com.smattme.springboot.hmacsignature.requests.TransferRequest;
import com.smattme.springboot.hmacsignature.response.TransferResponse;
import com.smattme.springboot.hmacsignature.services.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(Routes.TRANSFER)
    public ResponseEntity<TransferResponse> transferFund(@RequestBody TransferRequest transferRequest) {
        var response = transferService.transferFund(transferRequest);
        return ResponseEntity.status(response.statusCode()).body(response);
    }
}
