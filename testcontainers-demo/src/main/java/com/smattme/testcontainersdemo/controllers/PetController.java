package com.smattme.testcontainersdemo.controllers;

import com.smattme.testcontainersdemo.config.Routes;
import com.smattme.testcontainersdemo.dtos.PetDto;
import com.smattme.testcontainersdemo.requests.PetRequest;
import com.smattme.testcontainersdemo.responses.GenericResponse;
import com.smattme.testcontainersdemo.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping(Routes.Pets.PETS)
    public ResponseEntity<GenericResponse<PetDto>> create(@RequestBody PetRequest request) {
        var response = petService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(Routes.Pets.PETS)
    public ResponseEntity<GenericResponse<List<PetDto>>> getAll() {
        var response = petService.getAll();
        return ResponseEntity.ok(response);
    }



}
