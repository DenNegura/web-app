package com.endava.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @SequenceGenerator(name = "next_department_id", sequenceName = "next_department_id", allocationSize = 1)
    @GeneratedValue(generator = "next_department_id")
    private long departmentId;

    private String name;

    private String location;
}
