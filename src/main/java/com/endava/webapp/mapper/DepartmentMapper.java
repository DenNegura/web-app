package com.endava.webapp.mapper;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentDTO departmentToDepartmentDTO(Department department) {
        if (department == null) {
            return null;
        }
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setName(department.getName());
        departmentDTO.setLocation(department.getLocation());
        return departmentDTO;
    }

    public Department departmentDTOtoDepartment(DepartmentDTO departmentDTO) {
        if(departmentDTO == null) {
            return null;
        }
        Department department = new Department();
        department.setDepartmentId(departmentDTO.getDepartmentId());
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        return department;
    }
}
