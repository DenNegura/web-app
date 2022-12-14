package com.endava.webapp.dto;

import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;

class DepartmentDtoTest {

    private static Validator validator;

    private static DepartmentDTO department;

    @BeforeEach
    void setUp() {
        validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .defaultLocale(Locale.ENGLISH)
                .buildValidatorFactory()
                .getValidator();
        department = new DepartmentDTO(1L, "Administration", "Chisinau");
    }

    @Test
    void testDepartmentSuccessValidation() {
        // Given

        // When
        Set<ConstraintViolation<DepartmentDTO>> validate = validator.validate(department);

        // Then
        Assertions.assertTrue(validate.isEmpty());
    }
    @Test
    void testInvalidName() {
        // Given
        department.setName(null);

        // When
        Set<ConstraintViolation<DepartmentDTO>> validate = validator.validate(department);

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }
    @Test
    void testInvalidLocation() {
        // Given
        department.setLocation(null);

        // When
        Set<ConstraintViolation<DepartmentDTO>> validate = validator.validate(department);

        // Then
        Assertions.assertEquals(1, validate.size());
        Assertions.assertTrue(validate.stream().anyMatch(x -> x.getMessage().equals("must not be blank")));
    }
}
