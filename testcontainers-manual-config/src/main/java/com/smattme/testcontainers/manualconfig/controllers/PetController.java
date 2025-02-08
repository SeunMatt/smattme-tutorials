package com.smattme.testcontainers.manualconfig.controllers;


import com.smattme.testcontainers.manualconfig.config.Routes;
import com.smattme.testcontainers.manualconfig.entities.Pet;
import com.smattme.testcontainers.manualconfig.requests.PetRequest;
import com.smattme.testcontainers.manualconfig.responses.GenericResponse;
import com.smattme.testcontainers.manualconfig.services.PetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping(Routes.Pets.PETS)
    public Mono<GenericResponse<Pet>> create(@RequestBody PetRequest request) {
        return petService.create(request);
    }

    @GetMapping(Routes.Pets.PETS)
    public Mono<GenericResponse<List<Pet>>> getAll() {
        return petService.getAll();
    }



}
