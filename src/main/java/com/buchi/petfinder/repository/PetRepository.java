package com.buchi.petfinder.repository;

import com.buchi.petfinder.model.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {

    List<Pet> findByType(String type);

    List<Pet> findByAge(int age);

    List<Pet> findByNameContainingIgnoreCase(String name);
}