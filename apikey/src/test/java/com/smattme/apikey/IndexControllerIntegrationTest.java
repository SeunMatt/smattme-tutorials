package com.smattme.apikey;

import com.smattme.apikey.config.Routes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void whenIndex_thenReturnSuccess() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get(Routes.WEB_INDEX))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", equalTo(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo("Welcome to SMATTME")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.timestamp", notNullValue()));

    }
}
