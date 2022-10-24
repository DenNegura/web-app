package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.entity.Department;
import com.endava.webapp.mapper.DepartmentMapper;
import com.endava.webapp.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    private DepartmentMapper departmentMapper;

    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::departmentToDepartmentDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO findById(long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        return departmentMapper.departmentToDepartmentDTO(department);
    }

    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department saveDepartment = departmentMapper.departmentDTOtoDepartment(departmentDTO);
        saveDepartment = departmentRepository.save(saveDepartment);
        return departmentMapper.departmentToDepartmentDTO(saveDepartment);
    }
}
