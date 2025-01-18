package com.smattme.testcontainersdemo.services;

import com.smattme.testcontainersdemo.dtos.PetDto;
import com.smattme.testcontainersdemo.entities.Pet;
import com.smattme.testcontainersdemo.repositories.PetRepository;
import com.smattme.testcontainersdemo.requests.PetRequest;
import com.smattme.testcontainersdemo.responses.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public GenericResponse<PetDto> create(PetRequest request) {
        Pet pet = new Pet();
        pet.setColour(request.getColour());
        pet.setName(request.getName());
        pet = petRepository.save(pet);

        GenericResponse<PetDto> response = new GenericResponse<>();
        response.setData(new PetDto(pet));
        response.setMessage("Operation Successful");
        response.setStatus(true);
        return response;
    }


    public GenericResponse<List<PetDto>> getAll() {
        List<PetDto> pets = petRepository.getAll();
        GenericResponse<List<PetDto>> response = new GenericResponse<>();
        response.setData(pets);
        response.setMessage("Operation Successful");
        response.setStatus(true);
        return response;
    }


}
