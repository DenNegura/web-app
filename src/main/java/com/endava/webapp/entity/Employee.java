package com.endava.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


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

    @NotNull
    @NotEmpty
    @NotBlank
    // @Pattern(regexp = "^[A-Z][a-z]{2,}$") why don't patterns?
    private String firstName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String lastName;

    @Min(1L)
    private long departmentId;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private String phoneNumber;

    @Min(1L)
    private double salary;
}
