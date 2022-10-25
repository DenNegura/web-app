package com.endava.webapp.controller;

import com.endava.webapp.dto.EmployeeDTO;
import com.endava.webapp.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> employeesDTO = employeeService.findAll();
        return ResponseEntity.ok(employeesDTO);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable long employeeId) {
        EmployeeDTO employee = employeeService.findById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> save(@RequestBody @Validated EmployeeDTO employee) {
        EmployeeDTO saveEmployee = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveEmployee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable long employeeId,
                                              @RequestBody @Validated EmployeeDTO employee) {
        employee.setEmployeeId(employeeId);
        EmployeeDTO updateEmployee = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateEmployee);
    }
}
