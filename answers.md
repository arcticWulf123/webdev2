package com.week8.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.week8.activity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{
    
}