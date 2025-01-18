package com.smattme.testcontainersdemo.repositories;

import com.smattme.testcontainersdemo.dtos.PetDto;
import com.smattme.testcontainersdemo.entities.Pet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetRepository extends CrudRepository<Pet, Long> {


    @Query("""
        SELECT new com.smattme.testcontainersdemo.dtos.PetDto(p)
        FROM Pet p
    """)
    List<PetDto> getAll();

}
