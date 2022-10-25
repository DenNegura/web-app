package com.endava.webapp.controller;

import com.endava.webapp.dto.DepartmentDTO;
import com.endava.webapp.service.DepartmentService;
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
@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private DepartmentService departmentService;
    @InjectMocks
    private DepartmentController departmentController;
    @Autowired
    private JacksonTester<DepartmentDTO> departmentJacksonTester;

    private static final List<DepartmentDTO> DEPARTMENTS = List.of(
            new DepartmentDTO(1L, "Administration", "Chisinau"),
            new DepartmentDTO(2L, "IT", "USA")
    );

    @Test
    void testGetAllDepartments() throws Exception {
        // Given
        StringBuilder jsonContent = new StringBuilder().append("[");
        Iterator<DepartmentDTO> departmentIterator = DEPARTMENTS.iterator();
        jsonContent.append(departmentJacksonTester.write(departmentIterator.next()).getJson());
        while(departmentIterator.hasNext()) {
            jsonContent.append(",").append(departmentJacksonTester.write(departmentIterator.next()).getJson());
        }
        jsonContent.append("]");

        // When
        when(departmentService.findAll()).thenReturn(DEPARTMENTS);
        MockHttpServletResponse response = mvc.perform(
                        get("/departments")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(jsonContent.toString(), response.getContentAsString());
    }

    @Test
    void testGetDepartmentById() throws Exception {
        // Given
        long departmentId = 1;

        // When
        when(departmentService.findById(departmentId))
                .thenReturn(DEPARTMENTS.get(0));
        MockHttpServletResponse response = mvc.perform(
                        get("/departments/" + departmentId)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(departmentJacksonTester.write(DEPARTMENTS.get(0)).getJson(),
                response.getContentAsString());
    }

    @Test
    void testSaveNewDepartment() throws Exception {
        // Given
        DepartmentDTO department = new DepartmentDTO(3L, "Server", "Romania");
        ArgumentCaptor<DepartmentDTO> departmentCaptor = ArgumentCaptor.forClass(DepartmentDTO.class);

        // When
        when(departmentService.save(department)).thenReturn(department);
        MockHttpServletResponse response = mvc.perform(
                        post("/departments")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(departmentJacksonTester
                                        .write(department).getJson()))
                .andReturn().getResponse();
        verify(departmentService).save(departmentCaptor.capture());

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(department, departmentCaptor.getValue());
        Assertions.assertEquals(departmentJacksonTester.write(department).getJson(), response.getContentAsString());
    }

    @Test
    void testUpdateDepartment() throws Exception {
        // Given
        long departmentId = DEPARTMENTS.get(0).getDepartmentId();
        DepartmentDTO department = new DepartmentDTO(departmentId, "Server", "Romania");
        ArgumentCaptor<DepartmentDTO> departmentCaptor = ArgumentCaptor.forClass(DepartmentDTO.class);

        // When
        when(departmentService.save(department)).thenReturn(department);
        MockHttpServletResponse response = mvc.perform(
                        put("/departments/" + departmentId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(departmentJacksonTester
                                        .write(department).getJson()))
                .andReturn().getResponse();
        verify(departmentService).save(departmentCaptor.capture());

        // Then
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        Assertions.assertEquals(department, departmentCaptor.getValue());
        Assertions.assertEquals(departmentJacksonTester.write(department).getJson(), response.getContentAsString());
    }
}
