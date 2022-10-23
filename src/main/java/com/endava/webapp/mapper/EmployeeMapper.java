package com.endava.webapp.mapper;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.dto.EmployeeDTO;
import com.endava.webapp.entity.Department;
import com.endava.webapp.entity.Employee;
import com.endava.webapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;
    public EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        if(employee == null) {
            return null;
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setDepartmentId(employee.getDepartment().getDepartmentId());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeDTO.setSalary(employee.getSalary());
        return employeeDTO;
    }
    public Employee employeeDTOtoEmployee(EmployeeDTO employeeDTO) {
        if(employeeDTO == null) {
            return null;
        }
        DepartmentDTO departmentDTO =
                departmentService.findById(employeeDTO.getDepartmentId());
        Department department =
                departmentMapper.departmentDTOtoDepartment(departmentDTO);
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDepartment(department);
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setSalary(employeeDTO.getSalary());
        return employee;
    }
}
