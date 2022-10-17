package com.endava.webapp.repository;

import com.endava.webapp.entity.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Rollback
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    private static final List<Department> DEPARTMENTS = List.of(
            new Department(1L, "Administration", "Chisinau"),
            new Department(2L, "IT", "USA")
    );

    @Test
    void testFindAll() {
        // Given

        // When
        List<Department> departments = departmentRepository.findAll();
        // Then
        Assertions.assertEquals(DEPARTMENTS, departments);
    }

    @Test
    void testGetById() {
        // Given
        Long departmentId = 1L;

        // When
        Optional<Department> department = departmentRepository.findById(departmentId);

        // Then
        Assertions.assertTrue(department.isPresent());
        Assertions.assertEquals(department.get(), DEPARTMENTS.get(0));
    }

    @Test
    void testSave() {
        // Given
        Department department = new Department(3L, "Server", "Romania");

        // When
        Department saveDepartment = departmentRepository.save(department);
        Optional<Department> readDepartment = departmentRepository.findById(department.getDepartmentId());

        // Then
        Assertions.assertEquals(saveDepartment, department);
        Assertions.assertTrue(readDepartment.isPresent());
        Assertions.assertEquals(readDepartment.get(), department);
    }

    @Test
    void testUpdate() {
        // Given
        Department department = DEPARTMENTS.get(0);
        department.setName("Server");
        department.setLocation("Romania");

        // When
        Department updateDepartment = departmentRepository.save(department);
        Optional<Department> readDepartment = departmentRepository.findById(department.getDepartmentId());

        // Then
        Assertions.assertEquals(updateDepartment, department);
        Assertions.assertTrue(readDepartment.isPresent());
        Assertions.assertEquals(readDepartment.get(), department);
    }
}
