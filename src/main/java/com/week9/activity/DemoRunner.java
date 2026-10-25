package com.week9.activity;

import java.util.List;

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
        Department department = new Department();

        department.addEmployee(new Employee());
        department.addEmployee(new Employee());

        departmentRepository.saveAndFlush(department);

        // Clears cached objects, so findAll() reads from the database.
        entityManager.clear();

        List<Department> departments = departmentRepository.findAll();

        for (Department foundDepartment : departments) {
            System.out.println(
                    "Department ID: " + foundDepartment.getId()
                            + " | Employees: " + foundDepartment.getEmployees().size());
        }
    }

}
