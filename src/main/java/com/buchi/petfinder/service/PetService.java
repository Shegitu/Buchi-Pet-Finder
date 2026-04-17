package com.buchi.petfinder.service;

import com.buchi.petfinder.dto.*;
import com.buchi.petfinder.exception.ResourceNotFoundException;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final FileStorageService fileStorageService;

    public PetService(PetRepository petRepository,
                      FileStorageService fileStorageService) {
        this.petRepository = petRepository;
        this.fileStorageService = fileStorageService;
    }

    public PetIdResponse createPet(PetCreateRequest req) {

        Pet pet = new Pet();
        pet.setType(req.getType());
        pet.setGender(req.getGender());
        pet.setSize(req.getSize());
        pet.setAge(req.getAge());
        pet.setGoodWithChildren(req.getGoodWithChildren());
        pet.setSource("local");
        pet.setPhotos(new ArrayList<>());

        if (req.getPhotos() != null) {
            List<String> urls = req.getPhotos().stream()
                    .map(fileStorageService::saveFile)
                    .toList();
            pet.setPhotos(urls);
        }

        Pet saved = petRepository.save(pet);

        PetIdResponse res = new PetIdResponse();
        res.setPetId(saved.getId());
        return res;
    }

    public PetDTO getPetById(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        return map(pet);
    }

    public Pet getPetEntityById(String id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
    }

    public void deletePet(String id) {
        Pet pet = getPetEntityById(id);
        petRepository.delete(pet);
    }

    public PetDTO uploadPhotos(String id, List<MultipartFile> files) {

        Pet pet = getPetEntityById(id);

        List<String> urls = files.stream()
                .map(fileStorageService::saveFile)
                .toList();

        if (pet.getPhotos() == null) {
            pet.setPhotos(new ArrayList<>());
        }

        pet.getPhotos().addAll(urls);

        Pet saved = petRepository.save(pet);
        return map(saved);
    }

   private PetDTO map(Pet pet) {
    PetDTO dto = new PetDTO();
    dto.setId(pet.getId());
    dto.setName(pet.getName());
    dto.setType(pet.getType());
    dto.setAge(pet.getAge()); // ⚠️ use correct type (String or int)
    dto.setPhotos(pet.getPhotos());
    return dto;
} }