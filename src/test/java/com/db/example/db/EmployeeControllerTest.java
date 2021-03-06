package com.db.example.db;

import com.db.example.db.one.to.n.controllers.CompanyController;
import com.db.example.db.one.to.n.controllers.EmployeeController;
import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.dto.EmployeeDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.services.CompanyService;
import com.db.example.db.one.to.n.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_return_created_status_when_create_employee() throws Exception{

        //given
        Employee employee1 = new Employee(1L,"ocean","male");
        given(employeeService.createEmployee(any(Employee.class))).willReturn(true);

        //when
        ResultActions result = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee1)));

        //then
        result.andExpect(status().isCreated());
    }

    @Test
    public void should_return_all_employees() throws Exception{

        //given
        Employee employee1 = new Employee(1L,"ocean","male");
        Employee employee2 = new Employee(2L,"ocean","male");
        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1);
        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2);
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO1,employeeDTO2);
        given(employeeService.getAllEmployee()).willReturn(employees);

        //when
        ResultActions result = mockMvc.perform(get("/employees"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("ocean")))
                .andExpect(jsonPath("$[1].id",is(2)));
    }

    @Test
    public void should_return_employee_by_id() throws Exception{

        //given
        Employee employee1 = new Employee(1L,"ocean","male");
        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1);
        given(employeeService.getEmployeeById(any())).willReturn(employeeDTO1);

        //when
        ResultActions result = mockMvc.perform(get("/employees/1"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("ocean")));
    }

    @Test
    public void should_return_male_employee() throws Exception{

        //given
        Employee employee1 = new Employee(1L,"ocean","male");
        Employee employee2 = new Employee(1L,"ocean22","female");
        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1);
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO1);
        given(employeeService.getMaleEmployee()).willReturn(employees);

        //when
        ResultActions result = mockMvc.perform(get("/employees/male"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("ocean")));
    }

    @Test
    public void should_return_employees_by_pages() throws Exception{

        //given
        Employee employee1 = new Employee(1L,"ocean","male");
        Employee employee2 = new Employee(2L,"ocean22","female");
        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1);
        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2);
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO1,employeeDTO2);
        given(employeeService.getEmployeesByPage(anyInt(),anyInt())).willReturn(employees);

        //when
        ResultActions result = mockMvc.perform(get("/employees/page/1/size/1"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("ocean")))
                .andExpect(jsonPath("$[1].id",is(2)));
    }

    @Test
    public void should_return_no_centent_status_when_update_employee() throws Exception{

        //given
        Employee employee1 = new Employee("ocean","male");
        given(employeeService.updateEmployee(any(),any(Employee.class))).willReturn(true);

        //when
        ResultActions result = mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee1)));

        //then
        result.andExpect(status().isNoContent());
    }

    @Test
    public void should_return_no_centent_status_when_delete_employee() throws Exception{

        //given
        given(employeeService.deleteEmployee(any())).willReturn(true);

        //when
        ResultActions result = mockMvc.perform(delete("/employees/1"));

        //then
        result.andExpect(status().isNoContent());
    }
}
