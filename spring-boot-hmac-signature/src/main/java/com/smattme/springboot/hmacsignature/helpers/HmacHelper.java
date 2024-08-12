package com.smattme.springboot.hmacsignature.helpers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacHelper {


    public static String generateHMACSignature(String message, String secret) throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        byte[] signatureBytes = hmacSHA256.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(signatureBytes);
    }
}
