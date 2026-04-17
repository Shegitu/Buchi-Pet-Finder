package com.buchi.petfinder.service;

import com.buchi.petfinder.dto.PetSearchDTO;
import com.buchi.petfinder.dto.SearchRequest;
import com.buchi.petfinder.model.Pet;
import com.buchi.petfinder.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PetSearchService {

    private final PetRepository petRepository;
    private final ExternalPetService externalPetService;

    public PetSearchService(PetRepository petRepository,
                            ExternalPetService externalPetService) {
        this.petRepository = petRepository;
        this.externalPetService = externalPetService;
    }

    public List<PetSearchDTO> searchPets(SearchRequest req) {

        List<PetSearchDTO> result = new ArrayList<>();

        // LOCAL FIRST ✅
        List<Pet> localPets = petRepository.findAll();

        List<PetSearchDTO> local = localPets.stream()
                .map(this::mapLocal)
                .collect(Collectors.toList());

        result.addAll(local);

        // EXTERNAL API ✅
        List<Map<String, Object>> external =
                externalPetService.fetchPets(null, null, null, req.getLimit());

        List<PetSearchDTO> externalMapped = external.stream()
                .map(this::mapExternal)
                .collect(Collectors.toList());

        result.addAll(externalMapped);

        return result.stream().limit(req.getLimit()).collect(Collectors.toList());
    }

    private PetSearchDTO mapLocal(Pet pet) {
        PetSearchDTO dto = new PetSearchDTO();
        dto.setPetId(pet.getId());
        dto.setSource("local");
        dto.setType(pet.getType());
        dto.setGender(pet.getGender());
        dto.setSize(pet.getSize());
        dto.setAge(pet.getAge());
        dto.setGoodWithChildren(pet.isGoodWithChildren());
        dto.setPhotos(pet.getPhotos());
        return dto;
    }

    private PetSearchDTO mapExternal(Map<String, Object> map) {
        PetSearchDTO dto = new PetSearchDTO();
        dto.setPetId((String) map.get("pet_id"));
        dto.setSource("petfinder");
        dto.setType((String) map.get("type"));
        dto.setGender((String) map.get("gender"));
        dto.setSize((String) map.get("size"));
        dto.setAge((String) map.get("age"));
        dto.setGoodWithChildren((Boolean) map.get("good_with_children"));
        dto.setPhotos((List<String>) map.get("photos"));
        return dto;
    }
}