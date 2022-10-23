package com.endava.webapp.controller;

import com.endava.webapp.dto.EmployeeDTO;
import com.endava.webapp.service.EmployeeService;
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
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeController employeeController;
    @Autowired
    private JacksonTester<EmployeeDTO> employeeJacksonTester;

    private static final List<EmployeeDTO> EMPLOYEES = List.of(
            new EmployeeDTO(100L, "Steven", "King", 1L, "steven.king@gmail.com", "515-123-4567", 14000D),
            new EmployeeDTO(101L, "Neena", "Kochhar", 1L, "neena.kochhar@gmail.com", "515-321-7654", 5000D),
            new EmployeeDTO(102L, "Lex", "DeHaan", 2L, "lex.dehaan@gmail.com", "151-543-8745", 8000D),
            new EmployeeDTO(103L, "Alexander", "Hunold", 2L, "alexander.hunold@gmail.com", "252-987-6512", 7500D)
    );

    @Test
    void testGetAllEmployees() throws Exception {
        // Given
        StringBuilder jsonContent = new StringBuilder().append("[");
        Iterator<EmployeeDTO> employeeIterator = EMPLOYEES.iterator();
        jsonContent.append(employeeJacksonTester.write(employeeIterator.next()).getJson());
        while(employeeIterator.hasNext()) {
            jsonContent.append(",").append(employeeJacksonTester.write(employeeIterator.next()).getJson());
        }
        jsonContent.append("]");

        // When
        when(employeeService.findAll()).thenReturn(EMPLOYEES);
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
        when(employeeService.findById(employeeId))
                .thenReturn(EMPLOYEES.get(1));
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
        EmployeeDTO employee = new EmployeeDTO(104L, "Maik", "Novak", 2L, "maik.novack@gmail.com", "575-777-1234", 7000D);
        ArgumentCaptor<EmployeeDTO> employeeCaptor = ArgumentCaptor.forClass(EmployeeDTO.class);

        // When
        when(employeeService.save(employee)).thenReturn(employee);
        MockHttpServletResponse response = mvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(employeeJacksonTester
                                        .write(employee).getJson()))
                .andReturn().getResponse();
        verify(employeeService).save(employeeCaptor.capture());

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(employee, employeeCaptor.getValue());
        Assertions.assertEquals(employeeJacksonTester.write(employee).getJson(), response.getContentAsString());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        // Given
        long employeeId = EMPLOYEES.get(1).getEmployeeId();
        EmployeeDTO employee = new EmployeeDTO(employeeId, "Maik", "Novak", 2L, "maik.novack@gmail.com", "575-777-1234", 7000D);
        ArgumentCaptor<EmployeeDTO> employeeCaptor = ArgumentCaptor.forClass(EmployeeDTO.class);

        // When
        when(employeeService.save(employee)).thenReturn(employee);
        MockHttpServletResponse response = mvc.perform(
                        put("/employees/" + employeeId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(employeeJacksonTester
                                        .write(employee).getJson()))
                .andReturn().getResponse();
        verify(employeeService).save(employeeCaptor.capture());

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(employee, employeeCaptor.getValue());
        Assertions.assertEquals(employeeJacksonTester.write(employee).getJson(), response.getContentAsString());
    }
}
