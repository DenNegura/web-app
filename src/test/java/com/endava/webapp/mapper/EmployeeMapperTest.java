package com.endava.webapp.mapper;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.dto.EmployeeDTO;
import com.endava.webapp.entity.Department;
import com.endava.webapp.entity.Employee;
import com.endava.webapp.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {

    @Mock
    private DepartmentService departmentService;

    @Mock
    private DepartmentMapper departmentMapper;
    @InjectMocks
    private EmployeeMapper mapper;

    private static final List<DepartmentDTO> DEPARTMENTS_DTO = List.of(
            new DepartmentDTO(1L, "Administration", "Chisinau"),
            new DepartmentDTO(2L, "IT", "USA")
    );
    private static final List<Department> DEPARTMENTS = List.of(
            new Department(1L, "Administration", "Chisinau"),
            new Department(2L, "IT", "USA")
    );

    @Test
    void testEmployeeDTOtoEmployee() {
        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO(100L, "Steven", "King", 1L, "steven.king@gmail.com", "515-123-4567", 14000);

        // When
        when(departmentService.findById(1L)).thenReturn(DEPARTMENTS_DTO.get(0));
        when(departmentMapper.departmentDTOtoDepartment(DEPARTMENTS_DTO.get(0))).thenReturn(DEPARTMENTS.get(0));
        Employee employee = mapper.employeeDTOtoEmployee(employeeDTO);

        // Then
        Assertions.assertEquals(employee.getEmployeeId(), employeeDTO.getEmployeeId());
        Assertions.assertEquals(employee.getFirstName(), employeeDTO.getFirstName());
        Assertions.assertEquals(employee.getLastName(), employeeDTO.getLastName());
        Assertions.assertEquals(employee.getDepartment().getDepartmentId(), employeeDTO.getDepartmentId());
        Assertions.assertEquals(employee.getEmail(), employeeDTO.getEmail());
        Assertions.assertEquals(employee.getPhoneNumber(), employeeDTO.getPhoneNumber());
        Assertions.assertEquals(employee.getSalary(), employeeDTO.getSalary());

        Assertions.assertEquals(employee.getDepartment(), DEPARTMENTS.get(0));
        verify(departmentService).findById(1L);
        verify(departmentMapper).departmentDTOtoDepartment(DEPARTMENTS_DTO.get(0));
    }
    @Test
    void testEmployeeToEmployeeDTO() {
        // Given
        Employee employee = new Employee(100L, "Steven", "King", DEPARTMENTS.get(0), "steven.king@gmail.com", "515-123-4567", 14000);

        // When
        EmployeeDTO employeeDTO = mapper.employeeToEmployeeDTO(employee);

        // Then
        Assertions.assertEquals(employee.getEmployeeId(), employeeDTO.getEmployeeId());
        Assertions.assertEquals(employee.getFirstName(), employeeDTO.getFirstName());
        Assertions.assertEquals(employee.getLastName(), employeeDTO.getLastName());
        Assertions.assertEquals(employee.getDepartment().getDepartmentId(), employeeDTO.getDepartmentId());
        Assertions.assertEquals(employee.getEmail(), employeeDTO.getEmail());
        Assertions.assertEquals(employee.getPhoneNumber(), employeeDTO.getPhoneNumber());
        Assertions.assertEquals(employee.getSalary(), employeeDTO.getSalary());
    }
}
