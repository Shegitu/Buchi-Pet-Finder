package com.buchi.petfinder.controller;

import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable String id) {
        return petRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable String id, @RequestBody Pet petDetails) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            pet.setName(petDetails.getName());
            pet.setType(petDetails.getType());
            pet.setAge(petDetails.getAge());
            return petRepository.save(pet);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deletePet(@PathVariable String id) {
        petRepository.deleteById(id);
        return "Pet removed with id " + id;
    }
}