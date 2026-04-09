package com.buchi.petfinder.repository;

import com.buchi.petfinder.model.Adoption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionRepository extends MongoRepository<Adoption, String> {
    List<Adoption> findByCustomerId(String customerId);
    List<Adoption> findByPetId(String petId);
}