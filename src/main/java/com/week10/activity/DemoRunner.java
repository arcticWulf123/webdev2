package com.week10.activity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class DemoRunner implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;
    private final EntityManager entityManager;

    public DemoRunner(DepartmentRepository departmentRepository, EntityManager entityManager) {
        this.departmentRepository = departmentRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Department dept = new Department();
        dept.setName("IT");
        departmentRepository.save(dept);

        entityManager.flush();
        entityManager.clear();

        Department loaded = departmentRepository.findById(dept.getId()).orElseThrow();
        System.out.println("Department: " + loaded.getName());
    }
}
