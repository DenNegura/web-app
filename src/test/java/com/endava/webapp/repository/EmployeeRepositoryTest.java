package com.endava.webapp.repository;

import com.endava.webapp.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Rollback
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee(100L, "Steven", "King", 1L, "steven.king@gmail.com", "515-123-4567", 14000D),
            new Employee(101L, "Neena", "Kochhar", 1L, "neena.kochhar@gmail.com", "515-321-7654", 5000D),
            new Employee(102L, "Lex", "DeHaan", 2L, "lex.dehaan@gmail.com", "151-543-8745", 8000D),
            new Employee(103L, "Alexander", "Hunold", 2L, "alexander.hunold@gmail.com", "252-987-6512", 7500D)
    );
    @Test
    void testFindAll() {
        // Given

        // When
        List<Employee> employees = employeeRepository.findAll();

        // Then
        Assertions.assertEquals(EMPLOYEES, employees);
    }

    @Test
    void testGetById() {
        // Given
        Long employeeId = 101L;

        // When
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        // Then
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals(employee.get(), EMPLOYEES.get(1));
    }
    @Test
    void testSave() {
        // Given
        Employee employee = new Employee(104, "Rick", "Sanchez", 1, "rick.sanchez@gmail.com", "057-006-2013", 23000);

        // When
        Employee saveEmployee = employeeRepository.save(employee);
        Optional<Employee> readEmployee = employeeRepository.findById(saveEmployee.getEmployeeId());

        // Then
        Assertions.assertEquals(saveEmployee, employee);
        Assertions.assertTrue(readEmployee.isPresent());
        Assertions.assertEquals(readEmployee.get(), employee);
    }

    @Test
    void testUpdate() {
        // Given
        Employee updateEmployee = EMPLOYEES.get(1);
        updateEmployee.setFirstName("Nick");
        updateEmployee.setLastName("Milton");

        // When
        employeeRepository.save(EMPLOYEES.get(1));
        Employee resultEmployee = employeeRepository.save(updateEmployee);
        Optional<Employee> readEmployee = employeeRepository.findById(updateEmployee.getEmployeeId());

        // Then
        Assertions.assertEquals(resultEmployee, updateEmployee);
        Assertions.assertTrue(readEmployee.isPresent());
        Assertions.assertEquals(readEmployee.get(), updateEmployee);
    }
}
