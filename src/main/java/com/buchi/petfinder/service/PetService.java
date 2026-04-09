package com.buchi.petfinder.service;

import com.buchi.petfinder.dto.PetDTO;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.PetRepository;
import com.buchi.petfinder.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    private PetDTO mapToDTO(Pet pet) {
        return new PetDTO(
                pet.getId(),
                pet.getName(),
                pet.getType(),
                pet.getAge()
        );
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PetDTO getPetById(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));
        return mapToDTO(pet);
    }

    public PetDTO createPet(Pet pet) {
        return mapToDTO(petRepository.save(pet));
    }

    public PetDTO updatePet(String id, Pet petDetails) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));

        existingPet.setName(petDetails.getName());
        existingPet.setType(petDetails.getType());
        existingPet.setAge(petDetails.getAge());

        return mapToDTO(petRepository.save(existingPet));
    }

    public void deletePet(String id) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));

        petRepository.delete(existingPet);
    }
}