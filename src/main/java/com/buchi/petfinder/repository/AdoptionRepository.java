package com.buchi.petfinder.repository;

import com.buchi.petfinder.model.Adoption;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdoptionRepository extends MongoRepository<Adoption, String> {
    List<Adoption> findByPetId(String petId);
    List<Adoption> findByCustomerId(String customerId);
    List<Adoption> findByStatus(String status);
    List<Adoption> findByAdoptionDateBetween(String fromDate, String toDate);
}
