package com.db.example.db;

import com.db.example.db.one.to.n.controllers.CompanyController;
import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.dto.EmployeeDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.services.CompanyService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_return_all_company_with_details() throws Exception{
        //given
        Company company1 = new Company(1L,"oocl");
        Company company2 = new Company(2L,"olaei");
        CompanyDTO companyDTO1 = new CompanyDTO(company1);
        CompanyDTO companyDTO2 = new CompanyDTO(company2);
        List<CompanyDTO> companyDTOS = Arrays.asList(companyDTO1,companyDTO2);
        given(companyService.getAllCompany()).willReturn(companyDTOS);

        //when
        ResultActions result = mockMvc.perform(get("/companies"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("oocl")))
                .andExpect(jsonPath("$[1].id",is(2)));
    }

    @Test
    public void should_return_company_with_details_when_find_by_id() throws Exception{
        //given
        Company company1 = new Company(1L,"oocl");
        CompanyDTO companyDTO1 = new CompanyDTO(company1);
        given(companyService.getCompanyById(any())).willReturn(companyDTO1);

        //when
        ResultActions result = mockMvc.perform(get("/companies/1"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is(company1.getName())));
    }

    @Test
    public void should_return_company_with_details_when_given_page_and_pageSize() throws Exception{
        //given
        Company company1 = new Company(1L,"oocl");
        Company company2 = new Company(2L,"olaei");
        CompanyDTO companyDTO1 = new CompanyDTO(company1);
        CompanyDTO companyDTO2 = new CompanyDTO(company2);
        List<CompanyDTO> companyDTOS = Arrays.asList(companyDTO1,companyDTO2);
        given(companyService.getCompaniesByPage(anyInt(),anyInt())).willReturn(companyDTOS);

        //when
        ResultActions result = mockMvc.perform(get("/companies/page/1/pageSize/1"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("oocl")))
                .andExpect(jsonPath("$[1].id",is(2)));
    }

//    @Test
//    public void should_return_employee_with_details_when_find_by_company_id() throws Exception{
//        //given
//        Company company1 = new Company(1L,"oocl");
//        Company company2 = new Company(2L,"olaei");
//        Employee employee1 = new Employee("ocean",company1);
//        Employee employee2 = new Employee("kimmy",company2);
//        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1);
//        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2);
//        List<EmployeeDTO> employeeDTOs = Arrays.asList(employeeDTO1,employeeDTO2);
//        given(companyService.getEmployeesFromCompany(anyInt())).willReturn(employeeDTOs);
//
//        //when
//        ResultActions result = mockMvc.perform(get("/companies/1/employees"));
//
//        //then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$",hasSize(2)));
////                .andExpect(jsonPath("$[0].id",is(1)))
////                .andExpect(jsonPath("$[0].name",is("ocean")))
////                .andExpect(jsonPath("$[1].id",is(2)));
//    }

    @Test
    public void should_return_crated_status_when_update_a_company() throws Exception {

        //given
        Company company1 = new Company("oocl");
        when(companyService.updateCompany(any(Company.class))).thenReturn(true);
        //when
        ResultActions result = mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(company1)));
        //then
        result.andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    public void should_return_crated_status_when_create_a_company() throws Exception {

        //given
        Company company1 = new Company("oocl");
        when(companyService.createCompany(any(Company.class))).thenReturn(true);
        //when
        ResultActions result = mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(company1)));
        //then
        result.andExpect(status().isCreated())
                .andDo(print());
    }

//    @Test
//    public void should_return_company_when_delete_a_company() throws Exception {
//
//        //given
//        Company company1 = new Company("oocl");
//        when(companyService.deleteCompany(anyInt())).thenReturn(company1);
//        //when
//        ResultActions result = mockMvc.perform(delete("/companies/1"));
//
//        //then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.name",is("oocl")));
//    }

}
