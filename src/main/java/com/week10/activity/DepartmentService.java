package com.week10.activity;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    private DepartmentDTO toDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::toDTO);
    }

    public Department createDepartment(Department department) {
        // Check for duplicate name — return 409 Conflict if found
        boolean exists = departmentRepository.findAll().stream()
                .anyMatch(d -> d.getName().equals(department.getName()));
        if (exists) {
            throw new RuntimeException("Department name already exists");
        }
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        existing.setName(updatedDepartment.getName());
        return departmentRepository.save(existing);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}