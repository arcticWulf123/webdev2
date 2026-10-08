package com.week8.activity;

import java.util.List;

import org.springframework.stereotype.Service;

import com.week8.activity.repository.PetRepository;

import jakarta.transaction.Transactional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void registerPet(Pet p) {
        petRepository.save(p);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long id) {
        return petRepository.findAll().stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new PetNotFoundException("Pet with id" + id + " not found"));
    }

    public void updatePet(Pet p, Long id) {
        Pet toBeUpdated = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet with " + id + " not found..."));
        toBeUpdated.setBreed(p.getBreed());
        toBeUpdated.setName(p.getName());
        toBeUpdated.setSpecies(p.getSpecies());
        petRepository.save(toBeUpdated);
    }

    public void removePet(Long id) {
        Pet toBeRemoved = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet with id " + id + " not found"));
        petRepository.delete(toBeRemoved);
    }

    public Pet findSpeciesIgnoreCase(String species) {
        return petRepository.findBySpeciesIgnoreCase(species).stream().findFirst()
                .orElseThrow(() -> new PetNotFoundException("Could not find a pet with " + species + " as species"));
    }

    public Pet findByNameContainingIgnoreCase(String keyword) {
        return petRepository.findByNameContainingIgnoreCase(keyword).stream().findFirst()
                .orElseThrow(() -> new PetNotFoundException("Could not find a pet with " + keyword + " as a name"));
    }

    public List<String> findAllSpecies() {
        return petRepository.findAllSpecies();
    }

    @Transactional
    public void batchRegister(List<Pet> pets) {
        for (Pet p : pets) {
            petRepository.save(p);
        }
    }

}
