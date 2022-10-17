package com.endava.webapp.repository;

import com.endava.webapp.entity.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    @Override
    List<Department> findAll();
    @Override
    Optional<Department> findById(Long key);

    @Override
    Department save(Department entity);
}
