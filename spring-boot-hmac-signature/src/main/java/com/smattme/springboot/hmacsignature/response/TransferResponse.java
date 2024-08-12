package com.smattme.springboot.hmacsignature.response;

public record TransferResponse (boolean status, int statusCode, String statusMessage, String reference) {
}
