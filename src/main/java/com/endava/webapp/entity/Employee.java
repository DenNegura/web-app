package com.endava.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @SequenceGenerator(name = "next_employee_id", sequenceName = "next_employee_id", allocationSize = 1)
    @GeneratedValue(generator = "next_employee_id")
    private long employeeId;

    private String firstName;

    private String lastName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String email;

    private String phoneNumber;

    private double salary;
}
