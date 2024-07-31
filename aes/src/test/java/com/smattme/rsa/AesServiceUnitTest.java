package com.smattme.rsa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AesServiceUnitTest {


    @Test
    void givenAESKey_whenEncryptDataAESAndDecryptDataAES_thenGetSamePlainData() throws Exception {

        String aesKey = AesService.generateSecureAESKey(32);
        Assertions.assertEquals(32, aesKey.length());

        byte[] aesKeyBytes = aesKey.getBytes();

        String plainSecretData = "The World will end on ...";

        //encrypt
        String encryptedData = AesService.encryptDataAES(plainSecretData, aesKeyBytes);

       //decrypt=
        String decryptedData = AesService.decryptDataAES(encryptedData, aesKeyBytes);

        //assert
        assertEquals(plainSecretData, decryptedData);
    }


}
