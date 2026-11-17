package com.week10.activity;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody CreateDepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        try {
            Department created = departmentService.createDepartment(department);

            Long newId = created.getId();
            var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newId)
                    .toUri();

            DepartmentDTO dto = DepartmentDTO.from(created);
            return ResponseEntity.created(uri).body(dto);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Department name already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Department name already exists");
            }
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody UpdateDepartmentRequest request) {
        try {
            Department department = new Department();
            department.setName(request.getName());
            Department updated = departmentService.updateDepartment(id, department);
            DepartmentDTO dto = DepartmentDTO.from(updated);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Department name already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Department name already exists");
            }
            throw e;
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchDepartment(@PathVariable Long id, @RequestBody UpdateDepartmentRequest request) {
        DepartmentDTO department = departmentService.getDepartmentById(id)
                .map(d -> {
                    if (request.getName() != null) {
                        d.setName(request.getName());
                    }
                    return d;
                })
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        Department existing = new Department();
        existing.setId(department.getId());
        existing.setName(department.getName());

        if (request.getName() != null) {
            existing.setName(request.getName());
        }

        try {
            Department updated = departmentService.updateDepartment(id, existing);
            DepartmentDTO dto = DepartmentDTO.from(updated);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Department name already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Department name already exists");
            }
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}