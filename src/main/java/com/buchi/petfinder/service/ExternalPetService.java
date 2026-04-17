package com.buchi.petfinder.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ExternalPetService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> fetchPets(String type, String age, String gender, Integer limit) {

        String url = "https://api.thedogapi.com/v1/images/search?limit=10";

        try {
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            if (response == null) return new ArrayList<>();

            List<Map<String, Object>> result = new ArrayList<>();

            for (Map<String, Object> item : response) {

                if (limit != null && result.size() >= limit) break;

                Map<String, Object> pet = new HashMap<>();
                pet.put("pet_id", "ext-" + item.get("id"));
                pet.put("type", "Dog");
                pet.put("gender", "male");
                pet.put("size", "medium");
                pet.put("age", "adult");
                pet.put("good_with_children", true);
                pet.put("photos", List.of(item.get("url")));

                result.add(pet);
            }

            return result;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}