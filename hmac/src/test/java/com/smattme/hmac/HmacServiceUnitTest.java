package com.smattme.hmac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HmacServiceUnitTest {


    @Test
    void givenSecretKeyAndMessage_whenGenerateHMACSignature_thenReturnSignature() throws Exception {
        var secretKey = "demokeynotforproduction1234567890";
        var message = "Hello World";
        var signature = HmacService.generateHMACSignature(message, secretKey);
        assertNotNull(signature);
    }
}
