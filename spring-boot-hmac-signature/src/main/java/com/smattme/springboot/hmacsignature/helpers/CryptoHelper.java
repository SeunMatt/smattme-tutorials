package com.smattme.springboot.hmacsignature.helpers;

import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
public class CryptoHelper {


    @Value("${app.hmac-secret-key.encrypt-key}")
    private String hmacSecretKeyEncryptionKey;

    public static final int CRYPTO_AUTH_TAG_LENGTH = 128;
    public static final int CRYPTO_IV_LENGTH = 12;
    private static final String[] KEY_CHARS = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };


    public static String encryptDataAES(String plainData, byte[] key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key, "AES");

        //build the initialization vector
        byte[] iv = new byte[CRYPTO_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(CRYPTO_AUTH_TAG_LENGTH, iv); //128-bit authentication tag length

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        byte[] cipherText = cipher.doFinal(plainData.getBytes());

        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);


        //the first 12 bytes are the IV where others are the cipher message + authentication tag
        byte[] cipherMessage = byteBuffer.array();
        return Base64.getEncoder().encodeToString(cipherMessage);

    }


    public static String decryptDataAES(String encryptedDataInBase64, byte[] key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedDataInBase64.getBytes());

        //remember we stored the IV as the first 12 bytes while encrypting?
        byte[] iv = Arrays.copyOfRange(encryptedDataBytes, 0, CRYPTO_IV_LENGTH);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(CRYPTO_AUTH_TAG_LENGTH, iv); //128-bit authentication tag length
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        //use everything from 12 bytes on as ciphertext
        byte [] cipherBytes = Arrays.copyOfRange(encryptedDataBytes, CRYPTO_IV_LENGTH, encryptedDataBytes.length);

        byte[] plainText = cipher.doFinal(cipherBytes);

        return new String(plainText);

    }


    public String encryptDeviceCredential(String plainData) {
        if(Objects.isNull(plainData) || plainData.isEmpty()) return plainData;
        return Try.of(() -> encryptDataAES(plainData, hmacSecretKeyEncryptionKey.getBytes()))
                .get();
    }


    public String decryptDeviceCredential(String cipherText) {
        if(Objects.isNull(cipherText) || cipherText.isEmpty()) return cipherText;
        return Try.of( () -> decryptDataAES(cipherText, hmacSecretKeyEncryptionKey.getBytes()))
                .get();
    }


}
