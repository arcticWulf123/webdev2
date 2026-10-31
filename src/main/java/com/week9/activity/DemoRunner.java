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

    public DemoRunner(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository,
            EmployeeService employeeService, EntityManager entityManager, StudentRepository studentRepository,
            CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.entityManager = entityManager;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    @Override
    public void run(String... args) {
        // Create shared entities
        Student s = new Student();
        s.setName("Alice");
        studentRepository.save(s);

        Course c = new Course();
        c.setTitle("WebDev");
        courseRepository.save(c);

        // Link them
        Enrollment en = new Enrollment(s, c, LocalDate.now(), "A");
        s.addEnrollment(en);
        c.addEnrollment(en);
        enrollmentRepository.saveAndFlush(en);

        Long courseId = c.getId();

        // Delete student only. Enrollment should be removed by orphanRemoval, course
        // must survive
        studentRepository.delete(s);
        entityManager.flush();

        boolean courseExists = courseRepository.findById(courseId).isPresent();
        System.out.println("Course still exists: " + courseExists);
    }
}