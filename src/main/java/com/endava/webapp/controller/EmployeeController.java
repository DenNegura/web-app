package com.endava.webapp.controller;

import com.endava.webapp.entity.Employee;
import com.endava.webapp.repository.EmployeeRepository;
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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getById(@PathVariable long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return ResponseEntity.ok(employee.orElseThrow());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> save(@RequestBody @Validated Employee employee) {
        Employee saveEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveEmployee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> update(@PathVariable long employeeId, @RequestBody  @Validated Employee employee) {
        employee.setEmployeeId(employeeId);
        Employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateEmployee);
    }
}
