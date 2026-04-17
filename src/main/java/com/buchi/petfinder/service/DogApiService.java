package com.buchi.petfinder.service;

import com.buchi.petfinder.dto.ExternalDogDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DogApiService {

    private final RestTemplate restTemplate;

    @Value("${thedogapi.key:live_ZpN4I5R9bPq7gW0kL3m8nT2uV9xY1jA4B7cD0eF6hG3iK5lM}")
    private String apiKey;

    public DogApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getExternalPets(String breedId, Integer limit) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://api.thedogapi.com/v1/images/search")
                    .queryParam("limit", limit != null ? limit : 10)
                    .queryParam("has_breeds", "true")
                    .queryParam("breed_ids", breedId != null ? breedId : "abys")
                    .build().toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            List<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class)
                    .getBody();

            if (response == null || response.isEmpty()) {
                return new ArrayList<>();
            }

            return response.stream().map(img -> {
                Map<String, Object> pet = new HashMap<>();
                pet.put("pet_id", "dogapi-" + img.get("id"));
                pet.put("source", "dogapi");
                Object breedsObj = img.get("breeds");
                String type = "Dog";
                if (breedsObj instanceof List && !((List<?>) breedsObj).isEmpty()) {
                    Object breed0 = ((List<?>) breedsObj).get(0);
                    if (breed0 instanceof Map) {
                        type = (String) ((Map<?, ?>) breed0).get("name");
                    }
                }
                pet.put("type", type);
                pet.put("photos", List.of(img.get("url")));
                pet.put("gender", "male");
                pet.put("size", "medium");
                pet.put("age", "adult");
                pet.put("good_with_children", true);
                return pet;
            }).toList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}

