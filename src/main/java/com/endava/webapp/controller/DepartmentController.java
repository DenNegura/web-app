package com.endava.webapp.controller;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        List<DepartmentDTO> departmentsDTO = departmentService.findAll();
        return ResponseEntity.ok(departmentsDTO);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable long departmentId) {
        DepartmentDTO departmentDTO = departmentService.findById(departmentId);
        return ResponseEntity.ok(departmentDTO);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> save(@RequestBody @Validated DepartmentDTO department) {
        DepartmentDTO saveDepartment = departmentService.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveDepartment);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable long departmentId,
                                             @RequestBody @Validated DepartmentDTO department) {
        department.setDepartmentId(departmentId);
        DepartmentDTO updateDepartment = departmentService.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateDepartment);
    }
}
