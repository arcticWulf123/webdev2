package com.week8.activity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Pet")
public class Pet {
    @Column(name = "pet_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, name = "microchip_number")
    @NotBlank(message = "Microchip number is required")
    private String microChipNumber;
    @NotBlank(message = "Name is required")
    @Column
    private String name;
    @NotBlank(message = "Species is required")
    @Column
    private String species;
    @Column
    @NotBlank(message = "Breed is required")
    private String breed;

    public Pet(Long id, String microChipNumber, String name, String species, String breed) {
        this.id = id;
        this.microChipNumber = microChipNumber;
        this.name = name;
        this.species = species;
        this.breed = breed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicroChipNumber() {
        return microChipNumber;
    }

    public void setMicroChipNumber(String microChipNumber) {
        this.microChipNumber = microChipNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Pet() {

    }

}
