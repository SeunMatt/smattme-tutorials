package com.smattme.springboot.dbcolumnencryption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smattme.springboot.dbcolumnencryption.entities.UserSecretKey;
import com.smattme.springboot.dbcolumnencryption.repositories.UserSecretKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("test")
public class UserSecretKeyRepositoryUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserSecretKeyRepository userSecretKeyRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    void givenPlainTextSecret_whenSaveUserSecretKey_thenEncryptAndSave() {

        String plainTextSecret = "area57PassKey";
        String clientId = "demoClient";

        //create a new record
        UserSecretKey userSecretKey = new UserSecretKey();
        userSecretKey.setHmacSecretKey(plainTextSecret);
        userSecretKey.setClientId(clientId);

        //save the record
        userSecretKeyRepository.save(userSecretKey);

        //confirm the secret is encrypted
        String sql = "SELECT hmac_secret_key FROM user_secret_keys WHERE client_id='%s'"
                .formatted(clientId);
        String secretKeyInDB = jdbcTemplate.queryForObject(sql, String.class);

        //confirm they're not the same thing because secretKeyInDB is
        // the encrypted format.
        Assertions.assertNotEquals(plainTextSecret, secretKeyInDB);


        //read the data via repository
        UserSecretKey loadedUserSecretKey = userSecretKeyRepository
                .findFirstByClientId(clientId);

        //this will be true because automatic decryption has happened
        Assertions.assertEquals(plainTextSecret, loadedUserSecretKey.getHmacSecretKey());


    }





}
