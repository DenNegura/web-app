package com.endava.webapp.mapper;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.entity.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DepartmentMapperTest {

    private final DepartmentMapper mapper = new DepartmentMapper();

    @Test
    void testDepartmentDTOtoDepartment() {
        // Given
        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "Administration", "Chisinau");

        // When
        Department department = mapper.departmentDTOtoDepartment(departmentDTO);

        // Then
        Assertions.assertEquals(department.getDepartmentId(), departmentDTO.getDepartmentId());
        Assertions.assertEquals(department.getName(), departmentDTO.getName());
        Assertions.assertEquals(department.getLocation(), departmentDTO.getLocation());
    }
    @Test
    void testDepartmentToDepartmentDTO() {
        // Given
        Department department = new Department(1L, "Administration", "Chisinau");

        // When
        DepartmentDTO departmentDTO = mapper.departmentToDepartmentDTO(department);

        // Then
        Assertions.assertEquals(department.getDepartmentId(), departmentDTO.getDepartmentId());
        Assertions.assertEquals(department.getName(), departmentDTO.getName());
        Assertions.assertEquals(department.getLocation(), departmentDTO.getLocation());
    }
}
