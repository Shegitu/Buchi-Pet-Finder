package com.buchi.petfinder.service;

import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.PetRepository;
import com.buchi.petfinder.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(String id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet updatePet(String id, Pet petDetails) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));

        existingPet.setName(petDetails.getName());
        existingPet.setType(petDetails.getType());
        existingPet.setAge(petDetails.getAge());

        return petRepository.save(existingPet);
    }

    public void deletePet(String id) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));

        petRepository.delete(existingPet);
    }
}