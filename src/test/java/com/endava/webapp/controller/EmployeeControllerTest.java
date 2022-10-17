package com.endava.webapp.controller;

import com.endava.webapp.entity.Employee;
import com.endava.webapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@AutoConfigureJsonTesters
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeController employeeController;
    @Autowired
    private JacksonTester<Employee> employeeJacksonTester;

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee(100L, "Steven", "King", 1L, "steven.king@gmail.com", "515-123-4567", 14000D),
            new Employee(101L, "Neena", "Kochhar", 1L, "neena.kochhar@gmail.com", "515-321-7654", 5000D),
            new Employee(102L, "Lex", "DeHaan", 2L, "lex.dehaan@gmail.com", "151-543-8745", 8000D),
            new Employee(103L, "Alexander", "Hunold", 2L, "alexander.hunold@gmail.com", "252-987-6512", 7500D)
    );

    @Test
    void testGetAllEmployees() throws Exception {
        // Given
        StringBuilder jsonContent = new StringBuilder().append("[");
        Iterator<Employee> employeeIterator = EMPLOYEES.iterator();
        jsonContent.append(employeeJacksonTester.write(employeeIterator.next()).getJson());
        while(employeeIterator.hasNext()) {
            jsonContent.append(",").append(employeeJacksonTester.write(employeeIterator.next()).getJson());
        }
        jsonContent.append("]");

        // When
        when(employeeRepository.findAll()).thenReturn(EMPLOYEES);
        MockHttpServletResponse response = mvc.perform(
                get("/employees")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(jsonContent.toString(), response.getContentAsString());
    }

    @Test
    void testGetEmployeeById() throws Exception {
        // Given
        long employeeId = 101;

        // When
        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.ofNullable(EMPLOYEES.get(1)));
        MockHttpServletResponse response = mvc.perform(
                        get("/employees/" + employeeId)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(employeeJacksonTester.write(EMPLOYEES.get(1)).getJson(),
                response.getContentAsString());
    }

    @Test
    void testSaveNewEmployee() throws Exception {
        // Given
        Employee employee = new Employee(104L, "Maik", "Novak", 2L, "maik.novack@gmail.com", "575-777-1234", 7000D);
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        // When
        when(employeeRepository.save(employee)).thenReturn(employee);
        MockHttpServletResponse response = mvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(employeeJacksonTester
                                        .write(employee).getJson()))
                .andReturn().getResponse();
        verify(employeeRepository).save(employeeCaptor.capture());

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(employee, employeeCaptor.getValue());
        Assertions.assertEquals(employeeJacksonTester.write(employee).getJson(), response.getContentAsString());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        // Given
        long employeeId = EMPLOYEES.get(1).getEmployeeId();
        Employee employee = new Employee(employeeId, "Maik", "Novak", 2L, "maik.novack@gmail.com", "575-777-1234", 7000D);
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        // When
        when(employeeRepository.save(employee)).thenReturn(employee);
        MockHttpServletResponse response = mvc.perform(
                        put("/employees/" + employeeId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(employeeJacksonTester
                                        .write(employee).getJson()))
                .andReturn().getResponse();
        verify(employeeRepository).save(employeeCaptor.capture());

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(employee, employeeCaptor.getValue());
        Assertions.assertEquals(employeeJacksonTester.write(employee).getJson(), response.getContentAsString());
    }
}
