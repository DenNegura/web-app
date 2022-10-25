package com.endava.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private long departmentId;

    @NotBlank
    private String name;

    @NotBlank
    private String location;
}
