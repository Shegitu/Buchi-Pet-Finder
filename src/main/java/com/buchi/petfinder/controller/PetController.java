package com.buchi.petfinder.controller;

import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    // Get all pets
    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Get pet by ID
    @GetMapping("/{id}")
    public Optional<Pet> getPetById(@PathVariable String id) {
        return petRepository.findById(id);
    }

    // Add new pet
    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    // Update pet
    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable String id, @RequestBody Pet pet) {
        pet.setId(id);
        return petRepository.save(pet);
    }

    // Delete pet
    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable String id) {
        petRepository.deleteById(id);
    }
}