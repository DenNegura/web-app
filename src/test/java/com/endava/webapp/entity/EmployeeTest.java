package com.endava.webapp.entity;

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

class EmployeeTest {

    private static Validator validator;

    private static Employee employee;

    @BeforeEach
    void setUp() {
        validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .defaultLocale(Locale.ENGLISH)
                .buildValidatorFactory()
                .getValidator();
        employee = new Employee(100L, "Steven", "King", 1L, "steven.king@gmail.com", "515-123-4567", 14000);
    }

    @Test
    void testEmployeeSuccessValidation() {
        // Given

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);

        // Then
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    void testInvalidFirstName() {
        // Given
        employee.setLastName(null);

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(3, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be null")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be empty")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }

    @Test
    void testInvalidLastName() {
        // Given
        employee.setLastName(null);

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(3, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be null")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be empty")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }

    @Test
    void testInvalidDepartmentId() {
        // Given
        employee.setDepartmentId(0L);

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);
        Iterator<ConstraintViolation<Employee>> iterator = validate.iterator();

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertEquals("must be greater than or equal to 1", iterator.next().getMessage());
    }

    @Test
    void testInvalidEmailWhenNull() {
        // Given
        employee.setEmail(null);

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(3, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be null")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be empty")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }
    @Test
    void testInvalidEmailWhenErrorValue() {
        // Given
        employee.setEmail("email");

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);
        Iterator<ConstraintViolation<Employee>> iterator = validate.iterator();

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertEquals("must be a well-formed email address", iterator.next().getMessage());
    }
    @Test
    void testInvalidPhoneNumber() {
        // Given
        employee.setPhoneNumber(null);

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);

        // Then
        Assertions.assertEquals(3, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be null")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be empty")));
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }

    @Test
    void testInvalidSalary() {
        // Given
        employee.setSalary(0D);

        // When
        Set<ConstraintViolation<Employee>> validate = validator.validate(employee);
        Iterator<ConstraintViolation<Employee>> iterator = validate.iterator();

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertEquals("must be greater than or equal to 1", iterator.next().getMessage());
    }
}
