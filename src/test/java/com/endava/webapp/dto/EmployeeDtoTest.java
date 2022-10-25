package com.endava.webapp.dto;

import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

class EmployeeDtoTest {

    private static Validator validator;

    private static EmployeeDTO employee;

    @BeforeEach
    void setUp() {
        validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .defaultLocale(Locale.ENGLISH)
                .buildValidatorFactory()
                .getValidator();
        employee = new EmployeeDTO(100L, "Steven", "King", 1L, "steven.king@gmail.com", "515-123-4567", 14000);
    }

    @Test
    void testEmployeeSuccessValidation() {
        // Given

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);

        // Then
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    void testInvalidFirstName() {
        // Given
        employee.setLastName(null);

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }

    @Test
    void testInvalidLastName() {
        // Given
        employee.setLastName(null);

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }

    @Test
    void testInvalidDepartmentId() {
        // Given
        employee.setDepartmentId(0L);

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);
        Iterator<ConstraintViolation<EmployeeDTO>> iterator = validate.iterator();

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertEquals("must be greater than or equal to 1", iterator.next().getMessage());
    }

    @Test
    void testInvalidEmailWhenNull() {
        // Given
        employee.setEmail(null);

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }
    @Test
    void testInvalidEmailWhenErrorValue() {
        // Given
        employee.setEmail("email");

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);
        Iterator<ConstraintViolation<EmployeeDTO>> iterator = validate.iterator();

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertEquals("must be a well-formed email address", iterator.next().getMessage());
    }
    @Test
    void testInvalidPhoneNumber() {
        // Given
        employee.setPhoneNumber(null);

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }

    @Test
    void testInvalidSalary() {
        // Given
        employee.setSalary(0D);

        // When
        Set<ConstraintViolation<EmployeeDTO>> validate = validator.validate(employee);
        Iterator<ConstraintViolation<EmployeeDTO>> iterator = validate.iterator();

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertEquals("must be greater than or equal to 1", iterator.next().getMessage());
    }
}
