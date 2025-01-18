package com.smattme.testcontainersdemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smattme.testcontainersdemo.config.Routes;
import com.smattme.testcontainersdemo.entities.Pet;
import com.smattme.testcontainersdemo.repositories.PetRepository;
import com.smattme.testcontainersdemo.requests.PetRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PetControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PetRepository petRepository;


    @Test
    void givenValidRequestBody_whenCreate_thenReturn2XX() throws Exception {

        PetRequest request = new PetRequest();
        request.setName("Aja Ode");
        request.setColour("orange");

        mockMvc.perform(MockMvcRequestBuilders.post(Routes.Pets.PETS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Matchers.equalTo(request.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.colour", Matchers.equalTo(request.getColour())));
    }

    @Test
    void givenExistingPets_whenGetAll_thenReturnAllPets() throws Exception {

        Pet pet = new Pet();
        pet.setName("Blimey the Goat");
        pet.setColour("red");
        petRepository.save(pet);

        mockMvc.perform(MockMvcRequestBuilders.get(Routes.Pets.PETS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.greaterThanOrEqualTo(1)));

    }
}
