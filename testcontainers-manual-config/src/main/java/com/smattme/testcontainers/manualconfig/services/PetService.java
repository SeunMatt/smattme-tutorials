package com.smattme.testcontainers.manualconfig.services;
import com.smattme.testcontainers.manualconfig.entities.Pet;
import com.smattme.testcontainers.manualconfig.repositories.PetRepository;
import com.smattme.testcontainers.manualconfig.requests.PetRequest;
import com.smattme.testcontainers.manualconfig.responses.GenericResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Mono<GenericResponse<Pet>> create(PetRequest request) {
        Pet pet = new Pet();
        pet.setColour(request.getColour());
        pet.setName(request.getName());
        return petRepository.save(pet)
                .flatMap(GenericResponse::success)
                .onErrorResume(throwable -> GenericResponse.error(throwable.getMessage()));
    }


    public Mono<GenericResponse<List<Pet>>> getAll() {
        return petRepository.findAll().collectList()
                .flatMap(GenericResponse::success)
                .onErrorResume(throwable -> GenericResponse.error(throwable.getMessage()));
    }


}
