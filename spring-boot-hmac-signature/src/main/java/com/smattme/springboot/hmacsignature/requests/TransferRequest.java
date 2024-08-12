package com.smattme.springboot.hmacsignature.requests;

public record TransferRequest(String recipientAccountNumber, String recipientEmail, String sourceAccountNumber, Double amount, String currency, String reference) { }
