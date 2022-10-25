package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.entity.Department;
import com.endava.webapp.mapper.DepartmentMapper;
import com.endava.webapp.repository.DepartmentRepository;
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
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private static final List<Department> DEPARTMENTS = List.of(
            new Department(1L, "Administration", "Chisinau"),
            new Department(2L, "IT", "USA")
    );
    private static final List<DepartmentDTO> DEPARTMENTS_DTO = List.of(
            new DepartmentDTO(1L, "Administration", "Chisinau"),
            new DepartmentDTO(2L, "IT", "USA")
    );

    @Test
    void testFindAll() {
        // Given
        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);

        // When
        when(departmentRepository.findAll()).thenReturn(DEPARTMENTS);
        when(departmentMapper.departmentToDepartmentDTO(DEPARTMENTS.get(0)))
                .thenReturn(DEPARTMENTS_DTO.get(0));
        when(departmentMapper.departmentToDepartmentDTO(DEPARTMENTS.get(1)))
                .thenReturn(DEPARTMENTS_DTO.get(1));
        List<DepartmentDTO> departmentsDTO = departmentService.findAll();
        verify(departmentMapper, times(2))
                .departmentToDepartmentDTO(departmentCaptor.capture());

        // Then
        Assertions.assertEquals(DEPARTMENTS_DTO, departmentsDTO);
        Assertions.assertEquals(DEPARTMENTS, departmentCaptor.getAllValues());
        verify(departmentMapper).departmentToDepartmentDTO(DEPARTMENTS.get(0));
        verify(departmentMapper).departmentToDepartmentDTO(DEPARTMENTS.get(1));
        verify(departmentRepository).findAll();
    }
    
    @Test
    void testFindById() {
        // Given
        Department department = DEPARTMENTS.get(0);

        // When
        when(departmentRepository.findById(department.getDepartmentId())).
                thenReturn(Optional.of(department));
        when(departmentMapper.departmentToDepartmentDTO(department))
                .thenReturn(DEPARTMENTS_DTO.get(0));
        DepartmentDTO departmentDTO = departmentService.findById(department.getDepartmentId());

        // Then
        Assertions.assertEquals(DEPARTMENTS_DTO.get(0), departmentDTO);
        verify(departmentMapper).departmentToDepartmentDTO(department);
        verify(departmentRepository).findById(department.getDepartmentId());
    }

    @Test
    void testSave() {
        // Given
        Department department = DEPARTMENTS.get(0);
        DepartmentDTO departmentDTO = DEPARTMENTS_DTO.get(0);

        // When
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.departmentDTOtoDepartment(departmentDTO))
                .thenReturn(department);
        when(departmentMapper.departmentToDepartmentDTO(department))
                .thenReturn(departmentDTO);
        DepartmentDTO saveDepartmentDTO = departmentService.save(departmentDTO);

        // Then
        Assertions.assertEquals(departmentDTO, saveDepartmentDTO);
        verify(departmentMapper).departmentDTOtoDepartment(departmentDTO);
        verify(departmentMapper).departmentToDepartmentDTO(department);
        verify(departmentRepository).save(department);
    }
}
