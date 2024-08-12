package com.smattme.springboot.hmacsignature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smattme.springboot.hmacsignature.config.Routes;
import com.smattme.springboot.hmacsignature.entities.User;
import com.smattme.springboot.hmacsignature.entities.UserSecretKey;
import com.smattme.springboot.hmacsignature.helpers.HmacHelper;
import com.smattme.springboot.hmacsignature.repositories.UserRepository;
import com.smattme.springboot.hmacsignature.repositories.UserSecretKeyRepository;
import com.smattme.springboot.hmacsignature.requests.TransferRequest;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static com.smattme.springboot.hmacsignature.helpers.HmacHelper.generateHMACSignature;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("test")
public class TransferControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserSecretKeyRepository userSecretKeyRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void givenValidRequestSignatureAndBasicAuth_whenTransferFund_thenReturnOK()
            throws Exception {

//        User user = userRepository.findByEmail("olusola.cypher@example.com");
//
//        UserSecretKey userSecretKey = new UserSecretKey();
//        userSecretKey.setUser(user);
//        userSecretKey.setHmacSecretKey("karibuChangeMe");
//        userSecretKey.setClientId("olusola.cypher@example.com");
//        userSecretKey = userSecretKeyRepository.save(userSecretKey);
//
//        log.info("userSecretKey: {}", userSecretKey.getHmacSecretKey());
//
//        Thread.sleep(10000000000L);

        String basicAuthUsername = "olusola.cypher@example.com";
        String basicAuthPassword = "karibuChangeMe";
        String hmacSecret = "karibuChangeMe";
        String clientId = "olusola.cypher@example.com";

        var request = new TransferRequest("0123456789",
                "recipient.omni@example.com",
                "7U791239001", 2000.0,
                "NGN", UUID.randomUUID().toString());

        var timestamp = String.valueOf(System.currentTimeMillis());

        String requestString = objectMapper.writeValueAsString(request) + timestamp;
        String clientSignature = generateHMACSignature(requestString, hmacSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(basicAuthUsername, basicAuthPassword);
        headers.put("X-ClientId", List.of(clientId));
        headers.put("X-TimestampMillis", List.of(timestamp));
        headers.put("X-Signature", List.of(clientSignature));


        var expectMessage = "Fund transferred successfully";
        mockMvc.perform(MockMvcRequestBuilders.post(Routes.TRANSFER)
                        .content(objectMapper.writeValueAsString(request))
                        .headers(headers)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo(true)))
                .andExpect(jsonPath("$.statusCode", equalTo(200)))
                .andExpect(jsonPath("$.statusMessage", equalTo(expectMessage)))
                .andExpect(jsonPath("$.reference", equalTo(request.reference())));


    }


    @Test
    void givenNoSignature_whenIndex_thenReturnSuccessful()
            throws Exception {

        String basicAuthUsername = "olusola.cypher@example.com";
        String basicAuthPassword = "karibuChangeMe";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(basicAuthUsername, basicAuthPassword);

        mockMvc.perform(MockMvcRequestBuilders.get(Routes.INDEX)
                        .headers(headers)
                ).andExpect(status().isOk())
                .andExpect(content().string("Hello World"));

    }


    @Test
    void givenInvalidRequestSignature_whenTransferFund_thenReturn401Unauthorized()
            throws Exception {

        String basicAuthUsername = "olusola.cypher@example.com";
        String basicAuthPassword = "karibuChangeMe";
        String clientId = "olusola.cypher@example.com";

        var request = new TransferRequest("0123456789",
                "recipient.omni@example.com",
                "7U791239001", 2000.0,
                "NGN", UUID.randomUUID().toString());

        var timestamp = String.valueOf(System.currentTimeMillis());
        String requestString = objectMapper.writeValueAsString(request) + timestamp;

        //signature with the wrong secret key
        String clientSignature = generateHMACSignature(requestString, "wrongKey");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(basicAuthUsername, basicAuthPassword);
        headers.put("X-ClientId", List.of(clientId));
        headers.put("X-TimestampMillis", List.of(timestamp));
        headers.put("X-Signature", List.of(clientSignature));


        mockMvc.perform(MockMvcRequestBuilders.post(Routes.TRANSFER)
                        .content(objectMapper.writeValueAsString(request))
                        .headers(headers)
                )
                .andExpect(status().is(401));

    }
}
