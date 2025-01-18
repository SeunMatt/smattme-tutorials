package com.smattme.testcontainersdemo.dtos;

import com.smattme.testcontainersdemo.entities.Pet;

import java.time.LocalDateTime;
import java.util.Objects;

public class PetDto {

    private String id;
    private String name;
    private String colour;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public PetDto(Pet pet) {
        if(Objects.isNull(pet)) return;
        id = pet.getIdString();
        name = pet.getName();
        colour = pet.getColour();
        createdAt = pet.getCreatedAt();
        updatedAt = pet.getUpdatedAt();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
