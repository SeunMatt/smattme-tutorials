package com.smattme.testcontainers.manualconfig.repositories;


import com.smattme.testcontainers.manualconfig.entities.Pet;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PetRepository extends R2dbcRepository<Pet, Long> {

}
