package com.week10.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private static final int MAX_PAGE_SIZE = 100;

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

    public Page<DepartmentDTO> listDepartments(int page, int size, String sort, String direction, String name) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

        String property = sort == null ? "id" : sort.trim();
        String order = direction == null ? "asc" : direction.trim();

        if (property.contains(",")) {
            String[] parts = property.split(",", 2);
            property = parts[0].trim();
            if (parts.length > 1 && !parts[1].isBlank()) {
                order = parts[1].trim();
            }
        }

        if (!property.equals("id") && !property.equals("name")) {
            property = "id";
        }

        Sort sortOrder = order.equalsIgnoreCase("desc")
                ? Sort.by(property).descending()
                : Sort.by(property).ascending();

        Pageable pageable = PageRequest.of(safePage, safeSize, sortOrder);

        Page<Department> result = (name == null || name.isBlank())
                ? departmentRepository.findAll(pageable)
                : departmentRepository.findByNameContainingIgnoreCase(name.trim(), pageable);

        return result.map(this::toDTO);
    }

    public DepartmentDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    public Department createDepartment(Department department) {
        boolean exists = departmentRepository.findAll().stream()
                .anyMatch(d -> d.getName().equals(department.getName()));
        if (exists) {
            throw new DepartmentAlreadyExistsException("Department name already exists: " + department.getName());
        }
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        boolean nameTaken = departmentRepository.findAll().stream()
                .anyMatch(d -> d.getName().equals(updatedDepartment.getName()) && !d.getId().equals(id));
        if (nameTaken) {
            throw new DepartmentAlreadyExistsException("Department name already exists: " + updatedDepartment.getName());
        }

        existing.setName(updatedDepartment.getName());
        return departmentRepository.save(existing);
    }

    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
