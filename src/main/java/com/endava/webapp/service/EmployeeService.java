package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeDTO;
import com.endava.webapp.entity.Employee;
import com.endava.webapp.mapper.EmployeeMapper;
import com.endava.webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {


    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }
    public EmployeeDTO findById(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employeeMapper.employeeToEmployeeDTO(employee);
    }
    public EmployeeDTO save(EmployeeDTO employee) {
        Employee saveEmployee = employeeMapper.employeeDTOtoEmployee(employee);
        saveEmployee = employeeRepository.save(saveEmployee);
        return employeeMapper.employeeToEmployeeDTO(saveEmployee);
    }
}
