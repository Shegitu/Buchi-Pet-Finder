package com.buchi.petfinder.service;

import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Pet> getPetById(String id) {
        return petRepository.findById(id);
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet updatePet(String id, Pet petDetails) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id " + id));

        pet.setName(petDetails.getName());
        pet.setType(petDetails.getType());
        pet.setAge(petDetails.getAge());

        return petRepository.save(pet);
    }

    public void deletePet(String id) {
        petRepository.deleteById(id);
    }
}