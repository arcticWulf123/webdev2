package com.week9.activity;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class DemoRunner implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EntityManager entityManager;
    private final EnrollmentRepository enrollmentRepository;
    private final AppUserRepository appUserRepository;
    private final PetRepository petRepository;

    public DemoRunner(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository,
            EmployeeService employeeService, EntityManager entityManager, StudentRepository studentRepository,
            CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
            AppUserRepository appUserRepository, PetRepository petRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.entityManager = entityManager;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.appUserRepository = appUserRepository;
        this.petRepository = petRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Create the user first
        AppUser user = new AppUser(LocalDate.now(), "jade", "jade@example.com", null);
        appUserRepository.save(user);

        // Create the pet and link to the user
        Pet pet = new Pet("000123456789012", "Milo", "Dog", user);
        user.setPet(pet);
        petRepository.save(pet);
        appUserRepository.save(user);

        entityManager.flush();
        entityManager.clear();

        // Read back to prove the 1:1 link works
        AppUser loadedUser = appUserRepository.findById(user.getId()).orElseThrow();
        System.out.println("User: " + loadedUser.getUsername() + " owns " + loadedUser.getPet().getName());
    }
}
