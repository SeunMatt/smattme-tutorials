package com.smattme.rsa;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RsaServiceUnitTest {


    @Test
    void givenRSAKeyPairAndData_whenEncryptAndDecrypt_then() throws Exception {

        int rsaKeySize = 2048;
        KeyPair keyPair = RsaService.generateRsaKeyPair(rsaKeySize);
        assertNotNull(keyPair);

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        //for learning purpose, let's convert to base64 string
        String base64PublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String base64PrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());


        String plainSecretData = "The real name of Satoshi Nakomoto is ...";

        //encrypt
        RSAPublicKey rsaPublicKey = RsaService.base64StringToPublicKey(base64PublicKey);
        String encryptedData = RsaService.encryptDataRsa(plainSecretData, rsaPublicKey);

       //decrypt
        RSAPrivateKey rsaPrivateKey = RsaService.base64StringToPrivateKey(base64PrivateKey);
        String decryptedData = RsaService.decryptDataRsa(encryptedData, rsaPrivateKey);

        //assert
        assertEquals(plainSecretData, decryptedData);
    }


}
