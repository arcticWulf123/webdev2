package com.week9.activity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public DemoRunner(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository,
            EmployeeService employeeService) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) {
        Department dept = new Department();
        dept.setName("IT");
        departmentRepository.save(dept);

        Employee emp = new Employee();
        emp.setDepartment(dept);
        employeeRepository.save(emp);

        String deptName = employeeService.getDepartmentName(emp.getId());
        System.out.println("Department" + deptName);

        Employee e = employeeService.getEmployeeWithDepartment(emp.getId());
        System.out.println(e.getDepartment().getName());
    }
}
