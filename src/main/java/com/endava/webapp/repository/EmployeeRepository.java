package com.endava.webapp.repository;


import com.endava.webapp.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Override
    List<Employee> findAll();
    @Override
    Optional<Employee> findById(Long key);

    @Override
    Employee save(Employee entity);
}
