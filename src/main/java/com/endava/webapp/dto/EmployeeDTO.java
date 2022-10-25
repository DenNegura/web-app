package com.endava.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private long employeeId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Min(1L)
    private long departmentId;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @Min(1)
    private double salary;
}
