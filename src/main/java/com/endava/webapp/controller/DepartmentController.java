package com.endava.webapp.controller;

import com.endava.webapp.entity.Department;
import com.endava.webapp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getById(@PathVariable long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        return ResponseEntity.ok(department.orElseThrow());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> save(@RequestBody @Validated Department department) {
        Department saveDepartment = departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveDepartment);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> update(@PathVariable long departmentId, @RequestBody @Validated Department department) {
        department.setDepartmentId(departmentId);
        Department updateDepartment = departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateDepartment);
    }
}
