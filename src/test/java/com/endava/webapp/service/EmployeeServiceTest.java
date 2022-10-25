package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeDTO;
import com.endava.webapp.entity.Department;
import com.endava.webapp.entity.Employee;
import com.endava.webapp.mapper.EmployeeMapper;
import com.endava.webapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeService employeeService;

    private static final List<Department> DEPARTMENTS = List.of(
            new Department(1L, "Administration", "Chisinau")
    );
    private static final List<Employee> EMPLOYEES = List.of(
            new Employee(100L, "Steven", "King", DEPARTMENTS.get(0), "steven.king@gmail.com", "515-123-4567", 14000D),
            new Employee(101L, "Neena", "Kochhar", DEPARTMENTS.get(0), "neena.kochhar@gmail.com", "515-321-7654", 5000D)
    );
    private static final List<EmployeeDTO> EMPLOYEES_DTO = List.of(
            new EmployeeDTO(100L, "Steven", "King", 1L, "steven.king@gmail.com", "515-123-4567", 14000D),
            new EmployeeDTO(101L, "Neena", "Kochhar", 1L, "neena.kochhar@gmail.com", "515-321-7654", 5000D)
    );
    @Test
    void testFindAll() {
        // Given
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        // When
        when(employeeRepository.findAll()).thenReturn(EMPLOYEES);
        when(employeeMapper.employeeToEmployeeDTO(EMPLOYEES.get(0))).thenReturn(EMPLOYEES_DTO.get(0));
        when(employeeMapper.employeeToEmployeeDTO(EMPLOYEES.get(1))).thenReturn(EMPLOYEES_DTO.get(1));
        List<EmployeeDTO> employeesDTO = employeeService.findAll();
        verify(employeeMapper, times(2)).employeeToEmployeeDTO(employeeCaptor.capture());

        // Then
        Assertions.assertEquals(EMPLOYEES_DTO, employeesDTO);
        Assertions.assertEquals(EMPLOYEES, employeeCaptor.getAllValues());
        verify(employeeMapper).employeeToEmployeeDTO(EMPLOYEES.get(0));
        verify(employeeMapper).employeeToEmployeeDTO(EMPLOYEES.get(1));
        verify(employeeRepository).findAll();
    }

    @Test
    void testFindById() {
        // Given
        Employee employee = EMPLOYEES.get(0);

        // When
        when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
        when(employeeMapper.employeeToEmployeeDTO(employee)).thenReturn(EMPLOYEES_DTO.get(0));
        EmployeeDTO employeeDTO = employeeService.findById(employee.getEmployeeId());

        // Then
        Assertions.assertEquals(EMPLOYEES_DTO.get(0), employeeDTO);
        verify(employeeMapper).employeeToEmployeeDTO(employee);
        verify(employeeRepository).findById(employee.getEmployeeId());
    }
    @Test
    void testSave() {
        // Given
        EmployeeDTO employeeDTO = EMPLOYEES_DTO.get(0);
        Employee employee = EMPLOYEES.get(0);

        // When
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeDTO(employee)).thenReturn(employeeDTO);
        when(employeeMapper.employeeDTOtoEmployee(employeeDTO)).thenReturn(employee);

        EmployeeDTO saveEmployeeDTO = employeeService.save(employeeDTO);

        // Then
        Assertions.assertEquals(employeeDTO, saveEmployeeDTO);
        verify(employeeMapper).employeeToEmployeeDTO(employee);
        verify(employeeMapper).employeeDTOtoEmployee(employeeDTO);
        verify(employeeRepository).save(employee);
    }
}
