package com.week8.activity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController()
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping()
    public List<Pet> getPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatepet(@PathVariable("id") Long id, @Valid @RequestBody Pet pet) {
        petService.updatePet(pet, id);
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PostMapping()
    public ResponseEntity<Pet> registerPet(@Valid @RequestBody Pet pet) {
        petService.registerPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable("id") Long id) {
        petService.removePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/species-list")
    public ResponseEntity<List<String>> listAllSpecies() {
        return ResponseEntity.ok(petService.findAllSpecies());
    }

    @GetMapping("/species")
    public ResponseEntity<Pet> searchBySpeciesName(@RequestParam String name) {
        if (name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Species name must not be blank");
        }
        return ResponseEntity.ok(petService.findSpeciesIgnoreCase(name));
    }

    @GetMapping(value = "/search", params = "keyword")
    public ResponseEntity<Pet> searchByName(@RequestParam String keyword) {
        if (keyword.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search keyword must not be blank");
        }
        return ResponseEntity.ok(petService.findByNameContainingIgnoreCase(keyword));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Pet>> batchRegister(@Valid @RequestBody List<Pet> pets) {
        petService.batchRegister(pets);
        return ResponseEntity.status(HttpStatus.CREATED).body(pets);
    }

}
