package com.smattme.testcontainers.manualconfig.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smattme.testcontainers.manualconfig.BaseSpringBootTest;
import com.smattme.testcontainers.manualconfig.config.Routes;
import com.smattme.testcontainers.manualconfig.entities.Pet;
import com.smattme.testcontainers.manualconfig.repositories.PetRepository;
import com.smattme.testcontainers.manualconfig.requests.PetRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.apache.commons.lang3.RandomStringUtils.insecure;

public class PetControllerIntegrationTest extends BaseSpringBootTest {

    private static final Logger log = LoggerFactory.getLogger(PetControllerIntegrationTest.class);
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PetRepository petRepository;


    @Test
    void givenValidRequestBody_whenCreate_thenReturn2XX() throws Exception {

        PetRequest request = new PetRequest();
        request.setName("Aja Ode " + insecure().randomAlphabetic(5).toUpperCase());
        request.setColour("orange");

        webTestClient.post().uri(Routes.Pets.PETS)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.name").isEqualTo(request.getName())
                .jsonPath("$.data.colour").isEqualTo(request.getColour());
    }

    @Test
    void givenExistingPets_whenGetAll_thenReturnAllPets() throws Exception {

        Pet pet = new Pet();
        pet.setName("Blimey the Goat " + insecure().randomAlphabetic(5).toUpperCase());
        pet.setColour("red");

        StepVerifier.create(petRepository.save(pet))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();

        webTestClient.get().uri(Routes.Pets.PETS)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$..name").value(Matchers.hasItem(pet.getName()));

    }
}
